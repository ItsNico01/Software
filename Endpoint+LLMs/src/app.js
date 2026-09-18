import express  from "express";

const app = express();
let buildJson = {}


app.use(express.json());

let llmResponse = {}
app.get('/build', (req, res) => {
    res.json(llmResponse);

})

app.post('/build', (req, res) => {
    console.log(req.body)
    llmResponse = req.body
    res.send("Json added successfully with the following values: " + JSON.stringify(req.body));

})


//Umgebungsvariable für Port nutzen. Wenn nicht verfügbar: 3000
const port = process.env.PORT || 3000;
//Port + Funktion, die ausgeführt wird, wenn darauf gelauscht wird.
app.listen(port, () => {console.log(`Server is running on port ${port}...`)});