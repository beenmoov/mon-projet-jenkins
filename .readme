Projet CI/CD : Jenkins, Maven et Docker

Ce dépôt contient une application Java/Maven que j'ai configurée avec un pipeline d'Intégration et de Livraison Continues (CI/CD) via Jenkins et Docker.

L'objectif de ce projet est d'automatiser la compilation, l'exécution des tests unitaires, le packaging de l'application, la création d'une image Docker ainsi que son déploiement sur Docker Hub à chaque modification du code source.

Démarrage rapide (si vous avez déjà le projet et la configuration)

Si vous avez déjà cloné ce dépôt et effectué la configuration initiale de Jenkins sur votre machine, voici les étapes pour redémarrer et exécuter le projet :

1. Démarrer le serveur Jenkins

Assurez-vous que le démon Docker est actif sur votre machine, puis démarrez le conteneur Jenkins existant :

docker start jenkins-server



Accédez ensuite à l'interface web sur http://localhost:8080 et connectez-vous avec vos identifiants.

2. Lancer un build du pipeline

Manuellement : Depuis le tableau de bord Jenkins, entrez dans votre pipeline et cliquez sur Lancer un build (Build Now).

Automatiquement (via Webhook) : Si vous utilisez Ngrok pour exposer votre instance locale, relancez le tunnel (ngrok http 8080), mettez à jour l'URL du Webhook dans les paramètres de votre dépôt GitHub si l'adresse a changé, puis effectuez un git push pour déclencher le build automatiquement.

3. Tester l'image générée

Une fois le pipeline terminé avec succès, vous pouvez exécuter directement l'image fraîchement construite :

docker run --rm VOTRE-USERNAME-DOCKERHUB/mon-projet-jenkins:1.0.0



Prérequis (première installation)

Si vous installez ce projet pour la première fois sur votre machine locale, assurez-vous de disposer des outils suivants :

Docker (Docker Desktop sur Windows/Mac ou Docker Engine sur Linux)

Git

Un compte GitHub (avec un Personal Access Token)

Un compte Docker Hub (avec un Access Token)

(Optionnel) Java 17 (JDK) et Maven 3.9+ si vous souhaitez compiler le projet manuellement sans passer par Jenkins.

Structure du projet

.
├── pom.xml              # Configuration Maven et dépendances (Java 17, JUnit 5)
├── Dockerfile           # Instructions pour conteneuriser l'application Java
├── Jenkinsfile          # Définition du pipeline CI/CD (Pipeline as Code)
└── src/
    ├── main/java/...    # Code source de l'application
    └── test/java/...    # Tests unitaires



Installation et lancement de Jenkins (via Docker)

Pour éviter d'installer des dépendances lourde sur la machine hôte et permettre à Jenkins de construire des images Docker directement, j'exécute Jenkins dans un conteneur Docker configuré avec le client Docker et le socket de la machine hôte.

1. Créer une image Jenkins intégrant le client Docker

Créez un fichier Dockerfile.jenkins avec le contenu suivant :

FROM jenkins/jenkins:lts-jdk17
USER root
RUN apt-get update && apt-get install -y docker.io && rm -rf /var/lib/apt/lists/*
USER jenkins



2. Construire et démarrer le conteneur Jenkins

Lancez les commandes suivantes dans votre terminal :

docker build -t jenkins-docker -f Dockerfile.jenkins .

docker run -d \
  --name jenkins-server \
  -p 8080:8080 -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  --user root \
  jenkins-docker



3. Accéder à l'interface Jenkins

Ouvrez votre navigateur à l'adresse http://localhost:8080.

Récupérez le mot de passe administrateur initial via cette commande :

docker exec jenkins-server cat /var/jenkins_home/secrets/initialAdminPassword



Installez les plugins suggérés ainsi que Pipeline, Maven Integration, GitHub Integration et Credentials Binding.

Configuration de Jenkins pour le test

1. Configuration des outils (Manage Jenkins > Tools)

JDK : Ajoutez une installation nommée JDK-17.

Maven : Ajoutez une installation nommée Maven-3.9 (version 3.9.x).

2. Configuration des identifiants (Manage Jenkins > Credentials)

Ajoutez deux identifiants de type Username with password dans le domaine Global :

ID git_credentials : Votre nom d'utilisateur GitHub et votre Personal Access Token.

ID dockerhubpass : Votre identifiant Docker Hub et votre Access Token.

3. Adapter le Jenkinsfile

Avant de lancer le pipeline sur votre environnement, pensez à remplacer mon identifiant Docker Hub (beenmoov) dans le Jenkinsfile par le vôtre :

sh 'docker build -t VOTRE-USERNAME-DOCKERHUB/mon-projet-jenkins:1.0.0 .'



4. Créer le pipeline

Cliquez sur Nouveau Item > Pipeline.

Dans la section Pipeline, sélectionnez Pipeline script from SCM.

Choisissez Git, renseignez l'URL de votre dépôt, sélectionnez git_credentials et indiquez Jenkinsfile comme chemin de script.

Cliquez sur Sauvegarder, puis sur Lancer un build.

Tester l'application manuellement (sans Jenkins)

Si vous souhaitez simplement vérifier que l'application compile et fonctionne localement sans monter l'infrastructure Jenkins :

1. Se placer dans le dossier du projet

cd /chemin/vers/VOTRE-REPO



2. Compiler et lancer les tests avec Maven

mvn clean test package



3. Construire et exécuter l'image Docker localement

docker build -t mon-projet-test:1.0.0 .
docker run --rm mon-projet-test:1.0.0

