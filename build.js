// build.js — Mahdollistaa noden !INCLUDE -syntaksin käytön
// Tarkemmat käyttöohjeet APIDOCUMENTATION.md-tiedoston alussa.
const fs = require("fs");

// Skannaa APIDOCUMENTATION.md
let md = fs.readFileSync("APIDOCUMENTATION.md", "utf8");

// Upota sisältö docs/api-kansiosta !INCLUDE-syntaksilla
md = md.replace(/!INCLUDE\s+"([^"]+)"/g, (_, file) => {
  try {
    return fs.readFileSync(file, "utf8");
  } catch {
    return "";
  }
});

// Tallenna uusi tiedosto
fs.writeFileSync("APIDOCUMENTATION.generated.md", md);
console.log("✅ Built APIDOCUMENTATION.generated.md");
