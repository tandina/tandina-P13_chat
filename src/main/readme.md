<p align="center">
  <a href="https://goldstack.party">
        <h1 align="center">POC de la fonctionnalité de Chat</h1>
  </a>
</p>


l'objectif est de montré l'utilité d'avoir un service de chat dans l'application de réservation Your car, Your way afin que les clients puissent joindre l'entreprise ce qui permet une prise en charge rapide et instantanée.

# Pré-requis

Pour le backend j'utilise Mysql 8.0.34 community edtion, le fichier .sql ainsi que les paramètres de la base de donnée sont disponibles dans le dossier db>DatabaseManager.

BACKEND

une fois les dépendances Maven installer, vous pouvez exécuter ChatServer en premier et ChatClient afin que l'api tourne sur le port :1234 pour le côté client et 5334 pour le service client.

Utilisation

Une fois chaque server éxécuté une console est disponible pour chacun qui permet d'échanger en instantanée.

SERVEUR INTERMEDIAIRE.

Depuis la racine du projet, lancer une console, installer le package node js, npm install cors, npm install express et éxécuté le fichier intermédiaire server.js.
Sur postman définir l'url http://localhost:3500/sendData, type POST, dans body choisir JSON et corp de requête
{
"web interface client": "Bonjour, je ne trouve pas mon véhicule"
}

une fois la requête lancer elle devrait s'afficher dans la console du ChatServer.

MYSQL
la conversation entre le service client et le client est stocké afin de conserver un historique, fichier disponible dans ressources>chat.sql
