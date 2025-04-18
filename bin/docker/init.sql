-- mysql-init/01-schema.sql
CREATE DATABASE IF NOT EXISTS alcatraz_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE alcatraz_db;

CREATE TABLE IF NOT EXISTS utilisateurs (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nom_utilisateur VARCHAR(50) NOT NULL UNIQUE,
  mot_de_passe_hash VARCHAR(255) NOT NULL,
  email VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS sauvegardes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  utilisateur_id INT NOT NULL,
  nom_partie VARCHAR(100),
  zone_actuelle VARCHAR(50),
  date_sauvegarde DATETIME DEFAULT CURRENT_TIMESTAMP,
  etat_jeu_json TEXT,
  FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id)
);
