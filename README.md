[![GitHub release](https://img.shields.io/github/release/jonathanlermitage/tikione-c2e.svg)](https://github.com/jonathanlermitage/tikione-c2e/releases) [![license](https://img.shields.io/github/license/jonathanlermitage/tikione-c2e.svg)](https://github.com/jonathanlermitage/tikione-c2e/blob/master/LICENSE.txt) [![Github All Releases](https://img.shields.io/github/downloads/jonathanlermitage/tikione-c2e/total.svg)](https://github.com/jonathanlermitage/tikione-c2e/releases)

# TikiOne C2E

Télécharge vos magazines [CanardPC](https://www.canardpc.com/) (abo numérique) pour une lecture hors-ligne sur PC, tablette et smartphone.  
Fonctionne sous Windows, MacOS, Linux, BSD.

Trois branches sont développées :

* [master](https://github.com/jonathanlermitage/tikione-c2e) : la version stable de l'application pour PC.
* [desktop-x.y.z](https://github.com/jonathanlermitage/tikione-c2e/branches) : la prochaine version de l'application pour PC. Par exemple `desktop-1.3.11` pour la version 1.3.11.
* [android](https://github.com/jonathanlermitage/tikione-c2e/tree/android) : l'application pour Android 5 et supérieur, en cours de dev.

## Téléchargement et utilisation en ligne de commande

Téléchargez [la dernière release](https://github.com/jonathanlermitage/tikione-c2e/releases) et décompressez-là dans un répertoire accessible en écriture. 

Trois versions packagées existent : 
* avec un JRE Windows 64bits et ImageMagick (`c2e-x.y.z-withWin64JRE-withImageMagick.zip`), **recommandé**.
* avec un JRE Windows 64bits (`c2e-x.y.z-withWin64JRE.zip`).
* et sans JRE (`c2e-x.y.z.zip`).

### Windows

* placez-vous dans le répertoire de l'application et lancez une console (Maj + clic droit, "Ouvrir un invité de commande ici"). Tapez `c2e.cmd username password paramètres...` (les seuls paramètres obligatoires sont username et password, les autres sont optionnnels).
  * `username` et `password` sont votre identifiant et mot de passe à l'abonnement CanardPC numérique, ces paramètres sont obligatoires.
  * `-cpcXXX` télécharger le numéro XXX, par exemple `-cpc348`. Répétez ce paramètre pour télécharger plusieurs numéros, par exemple `-cpc348 -cpc349 -cpc350 -cpc351`.
  * `-cpcall` télécharger l'intégralité des numéros à votre disposition.
  * `-cpcmising` télécharge uniquement les numéros manquants.
  * `-nopic` ne pas téléchanger les images (un numéro contient 60~200Mo d'images, et ~500Ko de texte).
  * `-list` savoir quels numéros sont accessibles au téléchargement.
  * `-debug` affiche le détail du téléchargement dans un format proche de JSON.
  * `-resizeXX` redimensionne les images selon le ratio `XX` (ex: `-resize50` pour un ratio de 50%). Basé sur [ImageMagick](http://www.imagemagick.org), lequel doit être disponible dans le PATH ou packagé avec l'appli. Testé sous Windows uniquement, mais doit fonctionner partout où ImageMagick est disponible.
  * `-index` génère un sommaire CSV (`CPC-index.csv`) de tous les numéros disponibles au téléchargement, avec en détails la note, présence de DRM, poids au téléchargement, plateformes, etc. Attention, prévoir plusieurs dizaines de minutes pour ce traitement. Si le fichier `CPC-index.csv` existe déjà, il sera complété avec les numéros manquants.
  * `-proxy:address:port` utilise le proxy HTTP(S) définit par l'adresse `address` (nom de domaine ou adresse IP) et le port `port`. Cette option est généralement utile si vous vous connectez depuis le réseau d'un entreprise qui impose un proxy pour accéder au web.
  * `-sysproxy` utilise le proxy système.
  * `-dark` active par défaut le mode nuit.
  * `-up` télécharge toute nouvelle version de l'application (version ZIP minimale, sans JRE ni ImageMagick) dans le répertoire courant. Son installation reste à la charge de l'utilisateur (dézipper l'archive téléchargée).
  
La police de caractères par défaut est `RobotoSlab-Light` (celle utilisée sur le site CanardPC). Pour utiliser une autre police, déposez un fichier TTF (par exemple `Arial.ttf`) dans le répertoire de application (à côté de `c2e.cmd` et `c2e.sh`) : elle sera automatiquement utilisée.
      
Exemples :

* télécharger le numéro 348, tapez `c2e.cmd username password -cpc348`.  
* télécharger le numéro 348 sans les images, tapez `c2e.cmd username password -cpc348 -nopic`.  
* télécharger le numéro 348 réduire les images à 40% de leur taille originelle, tapez `c2e.cmd username password -cpc348 -resize40`.  
* télécharger plusieurs numéros à la fois, par exemple 348, 350 et 355, tapez `c2e.cmd username password -cpc348 -cpc350 -cpc355`.  
* télécharger l'intégralité des numéros disponibles, tapez `c2e.cmd username password -cpcall`.
* télécharger les numéros manquants, tapez `c2e.cmd username password -cpcmissing`.  
* générer le sommaire de l'intégralité des numéros disponibles, tapez `c2e.cmd username password -index`.
* télécharger le numéro 348 au travers du proxy HTTP(S) companygateway sur le port 3128, tapez `c2e.cmd username password -cpc348 -proxy:companygateway:3218`.  
* télécharger le numéro 348 au travers du proxy système, tapez `c2e.cmd username password -cpc348 -sysproxy`.  
    
Le fichier est généré (ou écrasé) dans le répertoire courant (là où est le programme) et porte le nom `CPCxxx-opts.html` où `xxx` est le numéro et `-opts` rappelle certains paramètres (`-nopic`, `-resize`), par exemple `CPC348-nopic.html`.

### MacOS, Linux, BSD

Téléchargez et décompressez la version packagée `c2e-x.y.z.zip`. Comme Windows, mais remplacez `c2e.cmd` par `./c2e.sh`.  
Java 8 doit être installé et accessible depuis le PATH (avec un Ubuntu récent, tapez `sudo apt-get install default-jre`). Aussi, `c2e.sh` doit être rendu exécutable : tapez `chmod +x c2e.sh`.  
Ce script est testé sous Ubuntu 16.04 LTS et devrait fonctionner sur la majorité des distributions Linux.

## Compilation

Il sagit d'un projet Kotlin (Java 8 jusqu'à la v1.2.2, Kotlin ensuite) construit avec Gradle. Installez un JDK8 et Gradle 3+, puis lancez `gradle jar` pour construire un applicatif dans le répertoire `build/libs` (ou `gradlew jar` pour utiliser le wrapper Gradle 4, conseillé).

## Avancement

Voir le [changelog](https://github.com/jonathanlermitage/tikione-c2e/blob/master/CHANGELOG.md) pour l'avancée des travaux.

## Contributeurs

* [guame](https://github.com/guame)

Merci !

## Guide de contribution

 * le projet reste sous [license MIT](https://github.com/jonathanlermitage/tikione-c2e/blob/master/LICENSE.txt) et doit respecter la license des modules tiers (librairies, images).
 * le programme doit pouvoir fonctionner sous Windows et Linux, et si possible MacOS et BSD. A titre personnel, je teste sous Windows 10 et [Lubuntu 16.04.3 LTS](http://lubuntu.net).
 * consultez la [liste de tickets ouvert](https://github.com/jonathanlermitage/tikione-c2e/issues) : cela pourrait vous donner des idées. N'hésitez pas à ouvrir de nouveaux tickets, que ce soit pour signaler un bug, proposer une amélioration ou une nouvelle fonctionnalité. Cette étape est facultative, mais elle a le mérite de laisser une trace et invite à la discussion.
 * conserver une qualité de code : 
   * toute amélioration ou nouvelle fonctionnalité doit être un minimum testée.
   * on développe en anglais (code et javadoc), mais les fichiers Markdown (`.md`) restent en français.
   * le code est formaté avec les règles par défaut d'[IntelliJ IDEA](https://www.jetbrains.com/idea/) (`Ctrl + Alt + L` : reformate de code, `Ctrl + Alt + O` : réorganise les imports).
   * nommez correctement vos commits : indiquez ce que vous avez voulu faire en quelques mots.
   * réduisez le nombre de commits au minimum via un `squash`, et faites un `rebase` avant de soumettre une Pull Request : l'historique Git doit rester cohérent.
 * n'hésitez pas à me contacter par email à *jonathan.lermitage@gmail.com*, ou ouvez un [ticket](https://github.com/jonathanlermitage/tikione-c2e/issues).

## Motivation

Lors du Kickstarter ayant financé la version numérique de CanardPC, une compatibilité Pocket avait été annoncée - Pocket permettant de télécharger une page web pour la consulter hors ligne. Des raisons techniques empêchent aujourd'hui CanardPC de respecter cette promesse.  
TikiOne C2E a pour objectif de contenter les canards laisés, en leur permettant de télécharger leurs magazines dans divers formats pour une lecture hors-ligne.  

## A savoir

* L'export d'un numéro peut mal fonctionner et certains articles être vides : recommencez simplement l'export, cela devrait fonctionner.
* Le programme se connecte avec votre compte CanardPC. Le site détecte cette connexion et vous force à vous authentifier à nouveau lorsque vous revennez via votre navigateur web. C'est le comportement normal du site (sans doute pour éviter le partage de compte), ne soyez donc pas surpris.
* les exports PDF et EPUB sont annulés car leur niveau d'intégration est loin d'égaler celui d'un HTML dit "responsive" (adapté au PC, tablettes et smartphones). Ce sont aussi des formats favorisant le piratage du magazine, phénomène que je souhaite minimiser autant que possible.

## Licence

Licence MIT. En d'autres termes, ce logiciel est libre de droits et gratuit, vous pouvez en faire ce que vous voulez.

## Outils

Je développe TikiOne C2E grâce à ces logiciels :

|Kotlin|
|:--|
|[![IntelliJ](https://raw.githubusercontent.com/jonathanlermitage/tikione-c2e/master/misc/logo_kotlin.png)](https://kotlinlang.org/)|

|JetBrains IntelliJ IDEA|
|:--|
|[![IntelliJ](https://raw.githubusercontent.com/jonathanlermitage/tikione-c2e/master/misc/logo_intellij.png)](https://www.jetbrains.com/idea/)|

|Gradle|
|:--|
|[![Gradle](https://raw.githubusercontent.com/jonathanlermitage/tikione-c2e/master/misc/logo_gradle.png)](https://gradle.org)|

|Oracle JDK|
|:--|
|[![JDK](https://raw.githubusercontent.com/jonathanlermitage/tikione-c2e/master/misc/logo_java.png)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)|

|meow ?|
|:--|
|![cats](https://raw.githubusercontent.com/jonathanlermitage/tikione-c2e/master/misc/cats.gif)|

