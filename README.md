# 🎬 WatchFilx API — Backend de la plateforme collaborative de visionnage en ligne

## 📖 Présentation du projet

L’API **WatchFilx** constitue la partie **backend** du projet universitaire WatchFilx.  
Elle expose une série d’**endpoints REST** qui permettent à la plateforme web de gérer les utilisateurs, les vidéos, les playlists collaboratives et les fonctionnalités de chat en temps réel.

Ce backend s’inscrit dans le développement d’une **alternative à WatchTogether** ([https://w2g.tv](https://w2g.tv)), en garantissant performance, sécurité et scalabilité.

---

## 🧱 Fonctionnalités principales (MVP)

- 👤 **Gestion des utilisateurs** : inscription, connexion, profils, authentification JWT.
- 🎬 **Gestion des vidéos** : ajout, suppression, lecture et synchronisation.
- 🎶 **Playlist collaborative** : modification et partage entre plusieurs utilisateurs.
- 💬 **Chat en temps réel** (via WebSocket).
- 🔒 **Sécurité** : gestion des rôles et protection des endpoints.
- 🧾 **Documentation de l’API** avec Swagger ou Postman.

---

## ⚙️ Stack technique

### Backend
- **Java 23**
- **Spring Boot 3+** (Web, Data JPA, Security, WebSocket)
- **Maven** — Gestion des dépendances
- **MySQL / PostgreSQL** — Base de données relationnelle

### Outils de gestion
- **GitHub** — Versionnement et branches
- **Trello** — Suivi des tâches et planification
- **Postman** — Tests des endpoints
- **WhatsApp** — Communication et réunions

---

## 🧩 Méthodologie

Le développement suit le **modèle en cascade**, structuré en plusieurs étapes :
1. **Analyse des besoins**
2. **Conception UML et schéma de la base de données**
3. **Développement du MVP**
4. **Tests unitaires et d’intégration**
5. **Améliorations et livrables du second semestre**

Chaque phase est accompagnée d’**artefacts de travail** : diagrammes, rapports, réunions, etc.

---

## 👨‍💻 Équipe de développement

| Nom | Rôle | Responsabilités principales |
|------|------|-----------------------------|
| NKEOUA Lionel  | Développeur Frontend  | Chef de projet, Planification, coordination, suivi Trello |
| Dalia bensaid  | Développeur Backend   | API Java Spring Boot       |
| Lina Ould Amer | Développeur Backend   | Intégration API Backoffice |
| Ines Chegroun  | Développeur Backend   | Intégration API Backoffice |

---

## 🚀 Installation et lancement

### 🧰 Prérequis
- **Java 23** installé
- **Maven 3+**
- **PostgreSQL** configuré

### ⚙️ Étapes d’installation
```bash
# Cloner le projet
git clone https://github.com/AvignonUnivCollab/api-watchflix.git
cd api-watchfilx

# Compiler et exécuter le projet
mvn spring-boot:run
