const express = require("express");
const bodyParser = require("body-parser");
const app = express();
const cors = require("cors");
const dgram = require("dgram");
const udpServer = dgram.createSocket("udp4");

app.use(cors());
app.use(bodyParser.json());

app.post("/sendData", (req, res) => {
  const data = JSON.stringify(req.body);
  const bufferData = Buffer.from(data, "utf-8");

  udpServer.send(bufferData, 0, bufferData.length, 1234, "127.0.0.1", (err) => {
    if (err) {
      console.error(err);
      res.status(500).send("Erreur lors de l'envoi des données.");
    } else {
      res.send("Données envoyées avec succès.");
    }
  });
});

udpServer.on("message", (msg, rinfo) => {
  console.log(`Message reçu du serveur UDP: ${msg.toString()}`);
});

udpServer.bind(12345);

app.listen(3500, () => {
  console.log("Serveur intermédiaire en cours d'écoute sur le port 3500.");
});
