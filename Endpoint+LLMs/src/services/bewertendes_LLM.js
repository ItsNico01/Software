import OpenAI from "openai";

const openAI = new OpenAI({
    timeout: 7200000,
});

const response = await openAI.responses.create({
    model: "gpt-5-mini",
    reasoning: {effort: "high"},
    tools: [
        {
            type: "web_search",
        },
    ],
    instructions:
        "Du bewertest Strukturen für das Spiel Minecraft." +
        "Du erhältst den Prompt als Text und die Struktur in Form einer JSON-Datei." +
        "Es gibt die Attribute x, y, z und Block." +
        "x, y und z sind die Koordinaten und Block die Blockart." +
        "Die Blockarten kannst du https://jd.papermc.io/paper/1.21.11/org/bukkit/Material.html entnehmen." +
        "Bei einer Tür darf nur die Koordinate der unteren Hälfte enthalten sein."+
        "Die Minecraft-Welt ist superflat, daher befindet sich der Boden bei y=-61." +
        "Die Bewertungskriterien lauten: Vollständigkeit, Platzierung, Funktionalität (falls vorhanden) und Kontexttreue" +
        "Vollständigkeit berücksichtigt: 1. Sind alle Blöcke miteinander verbunden? 2. Sind die Dächer, Böden und Wände geschlossen?, 3. Sind alle Elemente vorhanden, welche für die korrekte Nachbildung des Bauwerkes benötigt werden?" +
        "Platzierung berücksichtigt: 1. Ist das Bauwerk auf dem Boden platziert? 2. Sind die Türen erreichbar und korrekt platziert?" +
        "Funktionalität berücksichtigt: 1. Ist die Struktur betretbar? 2. Können die vorhandenen Gegenstände benutzt werden? 3. Ist die im Prompt intendierte Funktionalität gewährleistet?" +
        "Kontexttreue berücksichtigt: 1. Wurde die gewünschte Struktur gebaut? 2. Wurden die korrekten Maße, Materialien und Farben verwendet?" +
        "Wenn eine Fragestellung vollständig erfüllt wurde entspricht das der Bewertung 1." +
        "Wurde eine Fragestellung nicht erfüllt, entspricht das der Bewertung -1" +
        "Wurde eine Fragestellung teilweise erfüllt, entspricht das der Bewertung 0" +
        "Kann eine Fragestellung nicht bewertet werden, da beispielsweise keine Funktion oder Tür vorhanden ist, entfällt die Bewertung mit einem --" +
        "Gebe zu jeder bewerteten Fragestellung eine entsprechende Begründung an.",

    input: "Prompt: Den Eiffelturm.   Struktur: {Einfügen des JSON}"


});

console.log(response.output_text);