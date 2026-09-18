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
    instructions: "Du baust Strukturen für Minecraft." +
        "Gib das Ergebnis im JSON-Format zurück." +
        "Das JSON befindet sich im Format: [{x: int, y: int, z: int, block: XY}]" +
        "Es gibt die Attribute x, y, z und Block." +
        "x, y und z sind die Koordinaten und Block die Blockart." +
        "Die Blockarten kannst du https://jd.papermc.io/paper/1.21.11/org/bukkit/Material.html entnehmen." +
        "Bei einer Tür darf nur die Koordinate der unteren Hälfte enthalten sein."+
        "Die Minecraft-Welt ist superflat, daher befindet sich der Boden bei y=-61." +
        "Es dürfen auf keinen Fall Quellen in der Ausgabe enthalten sein!",

    input: "Eine Banane"


});
//Sende das JSON der LLM mit der Fetch API
try {
    console.log(JSON.parse(response.output_text))
    const build = JSON.parse(response.output_text)
    fetch("http://localhost:3000/build", {
        method: "POST",
        body: JSON.stringify({
            build
        }),
        headers: {
            "Content-Type": "application/json; charset=UTF-8"
        }
    }).then(res => res.text())
        .then(data => console.log(data))
        .catch(err => console.error(err));
} catch (e) {
    console.error(e);
}


