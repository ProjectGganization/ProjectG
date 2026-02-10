# Dokumentaatio
Järjestelmän alustava nimi: TicketGuru

## LOL G
- Antti Reinikainen
- Ramona Hirvonen
- Toni Parhiala
- Vinh Phuc Phuong
- Phong Nguyen
- Ngan Tran

## Johdanto
Projektissa toteutetaan tiketti- ja lipunmyyntijärjestelmä, joka on suunniteltu sekä loppuasiakkaille että lipunmyynnin ja järjestelmän hallinnan tarpeisiin. Järjestelmää käyttävät kolme pääkäyttäjäryhmää: asiakkaat, lipunmyyjät ja ylläpitäjät (admin), joilla kaikilla on omat roolinsa ja käyttöoikeutensa. 

Asiakas voi selata ja etsiä tulevia tapahtumia, suodattaa niitä esimerkiksi kaupungin tai hakusanan perusteella sekä ostaa ja hallita lippuja digitaalisesti. Lipunmyyjä käyttää järjestelmää asiakaspalvelutilanteissa nopeaan myyntiin, maksujen käsittelyyn sekä myyntitapahtumien seurantaan, ja kaikki toiminnot kirjataan järjestelmään. Ylläpitäjä vastaa järjestelmän turvallisuudesta ja hallinnasta, kuten käyttäjätilien ja käyttöoikeuksien ylläpidosta sekä tapahtumien näkyvyydestä. 

Järjestelmä toteutetaan web-pohjaisena sovelluksena, jossa backend on toteutettu Java Spring Boot REST APIlla ja frontend Reactilla TypeScriptiä hyödyntäen. Projektin lopputuloksena syntyy toimiva ja selkeä lipunmyyntijärjestelmä, joka tukee sujuvaa asiakaskokemusta, tehokasta lipunmyyntiä ja keskitettyä hallintaa. 

## Järjestelmän määrittely
Lipunmyyjä - Lipunmyyjä käyttää järjestelmää asiakaspalvelutilanteissa. Tehtävänä on tehdä myyntejä, käsittelee maksut ja hoitaa tarvittaessa peruutukset tai palautukset. Järjestelmän näkökulmasta lipunmyyjä on päivittäinen käyttäjä, jonka tekemät tapahtumat pitää kirjata (kuka teki, milloin teki, mitä myyntiin).

Asiakas - Asiakkaat ovat yksityishenkilöitä, jota haluavat selata tapahtumatarjontaa ja ostaa pääsylippuja. Käyttäjäryhmälle on tyypillistä satunnainen käyttö. Asiakkaalle tärkeää on luotettava palvelu, mistä saa välittömän ja selkeän vahvistuksen onnistuneesta maksusta ja lipun toimituksesta. Käyttää järjestelmää pääasiassa 	verkkoselaimella. 

Järjestelmän näkökulmasta asiakasryhmä on anonyymi (selailuvaiheessa) tai tunnistettu (kirjautumisen/oston jälkeen). Jokainen asiakkaan tekemä toiminto, kuten lipun varaus tai maksu, on voitava jäljittää yksilöllisen tilausnumeron ja asiakastiedon avulla mahdollisten reklamaatioiden vuoksi. 

Ylläpitäjä - Ylläpitäjä pitää huolen järjestelmän turvallisuudesta hallitsemalla ja valvomalla tapahtumia.  Ylläpitäjä pitää huolen myös käyttäjätilien oikeuksista ja luo tarvittaessa uusia käyttäjätilejä lipunmyyjille. Järjestelmän näkökulmasta ylläpitäjä on korkeimman tason käyttäjä.

### Käyttötapauskaavio
![UML Käyttötapauskaavio](docs/UMLTicketGuru.png)

### Käyttäjätarinat
#### Asiakas
- Asiakkaana haluan nähdä listan tulevista tapahtumista, jotta voin löytää kiinnostavia tapahtumia. 
- Asiakkaana haluan pystyä suodattamaan listaa, esimerkiksi tapahtuman tai kaupungin mukaan, jotta pystyn helpommin löytämään tapahtumia lähellä minua. 
- Asiakkaana haluan etsiä tapahtumia hakusanalla, esimerkiksi artistin nimi. 
- Asiakkaana haluan lisätä liput ostoskoriin, jotta voin ostaa useita lippuja eri tapahtumiin samalla kertaa. 
- Asiakkaana haluan pystyä tulostamaan ostamani liput ostamisen jälkeen. 
- Asiakkaana haluan nähdä ostamani lipun, joko sivustolta tai sitten omassa sähköpostissani. 
- Asiakkaana haluan ladata ostamani lipun laitteelleni. 
- Asiakkaana haluan pystyä luomaan oman käyttäjätilin, jotta tietoni pysyvät tallennettuna. 

#### Lipunmyyjä
- Lipunmyyjänä, haluan nähdä tuotteet nopeasti, jotta jonot eivät kasva
- Kun asiakas haluaa ostaa useita lippuja, haluan muuttaa määriä, ilman uudelleenhakua.
- Kun maksu on hyväksytty, haluan kuitin yhdellä painalluksella
- Kun asiakas palaa kuitin kanssa, haluan löytää myynnin numerolla, jotta voin tarkistaa tilanteen.
- Kun vuoro loppuu, haluan yhteenvedon / raportin vuorosta esim. myyntien lukumääristä.

#### Ylläpitäjä
- Ylläpitäjänä haluan kirjautua sisään, jotta järjestelmä pysyy turvallisena 
- Ylläpitäjänä haluan luoda uusia lipunmyyjien käyttäjätilejä, jotta lipunmyynti toimii luotettavasti 
- Ylläpitäjänä haluan muokata käyttäjäoikeuksia, jotta voin varmistaa oikean pääsyn eri näkymiin 
- Ylläpitäjänä haluan poistaa tai lukita käyttäjiä, jotta voin estää väärinkäytön 
- Ylläpitäjänä haluan poistaa tai piilottaa tapahtumia, jotta voin estää väärinkäytön 
- Ylläpitäjänä haluan tarkastella kaikkia tapahtumia yhdestä näkymästä, jotta hallinta on tehokasta 

## Käyttöliitymä

### Käyttöliittymäkaavio
Linkki: https://miro.com/app/board/uXjVGRbZFy4=/?focusWidget=3458764657994404708

## Tietokanta
Teksti

### Tietohakemisto

### _Users_                                                                 

Kenttä | Tyyppi | Kuvaus
------ | ------ | ------ 
user_id | int PK | Käyttäjän tunniste |
email | varchar(255) NOT NULL | Sisäänkirjautumiseen käytettävä sähköposti
password_hash | varchar(255) NOT NULL | Suojattu salasana
account_created | date | Milloin tili on tehty
account_status | varchar FK | Fk AccountStatus tauluun

### _AccountStatus_

Kenttä | Tyyppi | Kuvaus
------ | ------ | ------
account_status | varchar PK | Kertoo onko tili lukittu vai ei

### _Customers_

Kenttä | Tyyppi | Kuvaus
------ | ------ | ------
customer_id | int PK | Asiakkaan ID
firstname | varchar (50) NOT NULL| Etunimi
lastname | varchar (100) NOT NULL | Sukunimi
email | varchar (250) NOT NULL | Sähköpostiosoite
phone | varchar (25) NOT NULL | Puhelinnumero
user_id | int FK | FK Users tauluun, jos asiakkaalla on olemassaoleva tili

### _Sellers_

Kenttä | Tyyppi | Kuvaus
------ | ------ | ------
seller_id | int PK | Myyjän ID
name | varchar(50) NOT NULL | Myyjän nimi
email | varchar (250) | Myyjän sähköpostiosoite
phone | varchar (25) | Puhelinnumero
user_id | int FK | FK Users tauluun

### _Venues_

Kenttä | Tyyppi | Kuvaus
------ | ------ | ------
venue_id | int PK | Paikan tunniste
name | varchar(250) NOT NULL| Paikan nimi
address | varchar(250) NOT NULL | Paikan osoite
postalcode | varchar(5) FK | FK PostalCodes tauluun

### _PostalCode_

Kenttä | Tyyppi | Kuvaus
------ | ------ | ------
postalcode | varchar(5) PK | Postinumero
city | varchar (250) NOT NULL | Kaupungin nimi

### _Events_

Kenttä | Tyyppi | Kuvaus
------ | ------ | ------
event_id | int PK | Tapahtuman tunniste
title | varchar(250) NOT NULL | Tapahtuman nimi
description | varchar(250) | Kuvausteksti
photo | varbinary (max) | Tapahtuman kuva
start_time | datetime NOT NULL| Tapahtuman aloitusaika
end_time | datetime | Tapahtuman päättymisaika
event_status | varchar FK | FK EventStatus tauluun
venue_id | int FK | Missä tapahtuma järjestetään
category | varchar FK | FK Category tauluun

### _EventStatus_

Kenttä | Tyyppi | Kuvaus
------ | ------ | ------
event_status | varchar PK | Kertooko onko tapahtuma loppuumyyty

### _Category_

Kenttä | Tyyppi | Kuvaus
------ | ------ | ------
category | varchar PK | Tapahtuman kategoria

### _Tickets_

Kenttä | Tyyppi | Kuvaus
------ | ------ | ------
ticket_id | int PK | Lipun tunniste
ticket_type | varchar FK | FK TicketType tauluun
event_id | int FK | FK Events tauluun
unitprice | decimal NOT NULL | Lipun yksikköhinta
in_stock | int NOT NULL | Jäljellä oleva määrä
order_limit | int NOT NULL | Max määrä per tilaus

### _IssuedTickets_

Kenttä | Tyyppi | Kuvaus
------ | ------ | ------
issuedticket_id | int PK | Lipun id
order_id | int FK | FK Orders tauluun
qr_code | varchar NOT NULL | Uniikki tarkistuskoodi
ticket_id | int FK | FK Tickets tauluun

### _Ticket Types_

Kenttä | Tyyppi | Kuvaus
------ | ------ | ------
ticket_type | varchar FK | Lipputyypi

### _Orders_

Kenttä | Tyyppi | Kuvaus
------ | ------ | ------
order_id | int PK | Tilausnumero
customer_id | int FK | FK customers tauluun
date | datetime NOT NULL | Tilausajankohta
seller_id | int FK | FK sellers tauluun
is_refunded | boolean | Onko tilaus hyvitetty
is_paid | boolean | Onko tilaus maksettu
payment_method | varchar FK | FK paymentMethod tauluun

### _PaymentMethod_

Kenttä | Tyyppi | Kuvaus
------ | ------ | ------
payment_method | varchar PK | Maksutapa

### _OrderDetails_

Kenttä | Tyyppi | Kuvaus
------ | ------ | ------
order_id | int FK | FK Orders tauluun
ticket_id | int FK | FK Tickets tauluun
unitprice | decimal NOT NULL | Lipun yksikköhinta
quantity | int NOT NULL | Tilauksen lippujen määrä

### Käsitekaavio
![Käsitekaavio](docs/Käsitekaavio.png)

### Luokkakaavio
<img width="1003" height="463" alt="Näyttökuva 2026-02-10 170601" src="https://github.com/user-attachments/assets/9d8852f0-50f1-464d-b9f5-c00886a50c1a" />


### Relaatiotietokanta
![Relaatiotietokanta](docs/relaatiokaavio.png)
