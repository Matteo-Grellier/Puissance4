# Puissance4

## Lancement de l'application 

Pour lancer l'application à partir de l'étape 3 il suffit d'écrire `mvn javafx:dorun`,  
à partir de l'étape 1 ou 2 il faut lancer l'application de cette manière : `cd /puissance4/src/main/java` puis ``javac com/puissance4/App.java`` et ``java com/puissance3/App``
OU on peut aussi le faire de cette manière,  

Cliquer sur Run dans le fichier app.java : ![Capture d’écran 2021-10-12 212229](https://user-images.githubusercontent.com/46086160/137016912-5eb4fded-5d16-4b67-85e1-acd2c4e843e1.png)

## Problèmes connus

La partie réseau ne marche pas avec l'interface graphique mais fonctionne avec l'interface en ligne de commande.
Le programme ne peut pas être mis en pause avec system.in ou read() mais il aurait fallu faire un système de multithreading.

## Structure du projet 

Notre projet ressemble à ceci 