# Autentikaatio ja rekisteröityminen

## Yleiskuvaus

Sovelluksessa on kolmivaiheinen autentikaatiorakenne:

1. **Rekisteröityminen** — käyttäjä luo tilin (`/register`)
2. **Kirjautuminen** — käyttäjä kirjautuu sisään (`/signin`)
3. **Tilan hallinta** — kirjautumistila säilyy selaimessa (`AuthContext`)

---

## Backend

### Uudet endpointit

| Metodi | Reitti | Kuvaus | Auth |
|--------|--------|--------|------|
| `POST` | `/api/register` | Luo uuden käyttäjätilin | Julkinen |
| `POST` | `/api/auth/login` | Kirjautuu sisään, palauttaa käyttäjän tiedot | Julkinen |

---

### POST /api/register

**Tiedosto:** `controller/RegisterController.java`

Luo samalla kertaa kaksi tietokantariviä:
- **Users** — sähköposti ja bcrypt-hastattu salasana
- **Customers** — nimi, sähköposti ja puhelinnumero, linkitetty Users-riviin

**Request body:**
```json
{
  "firstname": "Matti",
  "lastname": "Meikäläinen",
  "email": "matti@esimerkki.fi",
  "phone": "0401234567",
  "password": "salasana123"
}
```

**Vastaukset:**
- `201 Created` — rekisteröityminen onnistui, palauttaa `{ userId, email }`
- `409 Conflict` — sähköposti on jo käytössä
- `400 Bad Request` — virheellinen syöte

**Validointi:** `RegisterRequest` käyttää Bean Validation -annotaatioita (`@NotBlank`, `@Email`, `@Size(min=8)`). Controller käyttää `@Valid`-annotaatiota, joten virheellinen syöte palauttaa automaattisesti `400 Bad Request`.

**DTO:** `dto/RegisterRequest.java`

---

### POST /api/auth/login

**Tiedosto:** `controller/AuthController.java`

Käyttää Springin `AuthenticationManager`ia tunnistetietojen tarkistamiseen. Onnistuneen kirjautumisen jälkeen asettaa `SecurityContext`in ja luo HTTP-session, jotta suojatut API-kutsut toimivat kirjautumisen jälkeen. Ei käytä Spring Securityn oletuskirjautumisreittiä (`/login`), koska se palauttaa HTML-redirectejä eikä sovi React SPA:lle.

**Request body:**
```json
{
  "email": "matti@esimerkki.fi",
  "password": "salasana123"
}
```

**Vastaukset:**
- `200 OK` — kirjautuminen onnistui, palauttaa `{ email }`
- `401 Unauthorized` — väärä sähköposti tai salasana
- `400 Bad Request` — puuttuvat kentät

---

### SecurityConfig muutokset

**Tiedosto:** `config/SecurityConfig.java`

Lisätty julkiset reitit:
```java
.requestMatchers(HttpMethod.POST, "/api/register", "/api/auth/login").permitAll()
```

Lisätty `AuthenticationManager` beanina, jotta `AuthController` voi injektoida sen:
```java
@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
}
```

---

## Frontend

### AuthContext

**Tiedosto:** `src/context/AuthContext.tsx`

React Context, joka hallitsee kirjautumistilaa koko sovelluksessa.

- Tallentaa kirjautuneen käyttäjän `localStorage`een — tila säilyy sivun päivityksen yli
- Tarjoaa `login(email, password)` ja `logout()` -funktiot kaikille komponenteille
- Kääritty `App.tsx`:ssä koko sovelluksen ympärille `AuthProvider`illa

**Käyttö komponentissa:**
```tsx
const { user, login, logout } = useAuth();
```

---

### Kirjautumisvirta

```
Käyttäjä täyttää lomakkeen (SignIn.tsx)
        ↓
login(email, password) kutsutaan (AuthContext)
        ↓
POST /api/auth/login → backend tarkistaa tunnisteet
        ↓
Onnistui: { email } tallennetaan localStorageen ja Reactin tilaan
        ↓
Ohjaus etusivulle (/)
        ↓
Navbar näyttää sähköpostin ja "Sign out" -napin
```

---

### Rekisteröitymisvirta

```
Käyttäjä täyttää lomakkeen (RegisterPage.tsx)
        ↓
POST /api/register → backend luo User + Customer
        ↓
Onnistui: ohjaus kirjautumissivulle (/signin)
        ↓
Käyttäjä kirjautuu sisään luomillaan tunnuksilla
```

---

### Muutetut tiedostot

| Tiedosto | Muutos |
|----------|--------|
| `src/App.tsx` | `AuthProvider` lisätty koko sovelluksen ympärille |
| `src/pages/SignIn.tsx` | Kutsuu nyt oikeaa backendiä `useAuth().login()` kautta |
| `src/layouts/Navbar.tsx` | Näyttää kirjautuneena sähköpostin + uloskirjautumisnapin |
| `src/api/apiClient.ts` | `credentials: 'include'` lisätty ja virheeseen mukaan HTTP-status koodi |
| `src/pages/RegisterPage.tsx` | Virhekäsittely erottaa 409 (sähköposti käytössä) ja 400 (validointivirhe) |

### Uudet tiedostot

| Tiedosto | Kuvaus |
|----------|--------|
| `src/context/AuthContext.tsx` | Kirjautumistilan hallinta |
| `src/pages/RegisterPage.tsx` | Rekisteröitymislomake (`/register`) |

---

## Testausohjeet

### Manuaalinen testaus selaimessa

1. Käynnistä backend ja frontend
2. Avaa `http://localhost:3000/register`
3. Täytä lomake ja lähetä → pitäisi ohjata `/signin`-sivulle
4. Kirjaudu juuri luomillasi tunnuksilla → pitäisi ohjata etusivulle
5. Tarkista että Navbar näyttää sähköpostiosoitteen ja "Sign out" -napin
6. Klikkaa "Sign out" → Navbar palaa "Sign In" -tilaan

### Testaus Swaggerilla

Swagger-UI löytyy osoitteesta `http://localhost:8080/swagger-ui.html`

**Rekisteröityminen:**
```json
POST /api/register
{
  "firstname": "Testi",
  "lastname": "Käyttäjä",
  "email": "testi@esimerkki.fi",
  "phone": "0401234567",
  "password": "salasana123"
}
```
Odotettava vastaus: `201 Created`

**Kirjautuminen:**
```json
POST /api/auth/login
{
  "email": "testi@esimerkki.fi",
  "password": "salasana123"
}
```
Odotettava vastaus: `200 OK` → `{ "email": "testi@esimerkki.fi" }`

### Virheskenaariot

| Skenaario | Odotettava vastaus | Virheilmoitus käyttäjälle |
|-----------|-------------------|--------------------------|
| Rekisteröidy samalla sähköpostilla uudelleen | `409 Conflict` | "This email is already in use." |
| Tyhjä kenttä tai virheellinen sähköposti | `400 Bad Request` | "Please check your details. Password must be at least 8 characters." |
| Salasana alle 8 merkkiä | `400 Bad Request` | "Please check your details. Password must be at least 8 characters." |
| Väärä salasana kirjautuessa | `401 Unauthorized` | "Invalid email or password. Please try again." |

---

## Rajoitukset

- **Ei JWT-tokeneita** — autentikaatio perustuu Spring Securityn HTTP-sessioon ja selainpuolen localStorageen. Toimii SPA-käytössä, mutta ei sovi esim. mobiiliappeihin tai mikropalveluihin.
- **PostgreSQL kehityksessä** — käyttäjät säilyvät tietokannassa uudelleenkäynnistyksen yli (toisin kuin aiemmin H2:lla).
