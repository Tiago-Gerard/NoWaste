<?php
/*
	Projet:			Eat@School - Projet réalisé dans le cadre du module 306 - Réaliser un petit projet informatique
	
	Page: 			index.php
	Description:            Retourne l'Utilisateur en fonction des infomation envoyer en header
 	
	Auteur:			Tiago Gerard
*/

// Require du PDO
require "../pdo.php";

//Les requettes preparées sous forme d'un singleton
static $requestLogin = NULL;
static $requestGetOffer=NULL;
static $requettteImage=NULL;
static $requettePos = NULL;
static $requettePosExiste = NULL;
static $requestOffre = NULL;
static $requestGetType=NULL;


// Identifie si la si les headers envoyer avec la requette HTTP sont correcte
function login($numero,$pwd){
    //mise en place d'un singleton pour gagner du temp sur les requette si elle ont deja été faites une fois
    if($requestLogin==NULL){
        $db = getDB();
        $request = $db->prepare("SELECT numero FROM `Utilisateur` WHERE numero=:numero");
    }
    
    $request->bindParam(':numero',$numero);
    $request->execute();
    
    $data = $request->fetchAll(PDO::FETCH_ASSOC);		
        if(sha1($data['numero'] . "lapinfuta45")==$pwd)
            {
            return true;
            }
        else{
            return json_encode("Access denied");
        }
    return json_encode($array);
}


//
function verifieChangementImage($lien,$idOffre)
{
   
    if($requetteImage==NULL){
        $db = getDB();
        $requetteImage = $db->prepare("SELECT `lienPhoto` FROM `Offre` WHERE `idOffre`=:idOffre");
    }
    $requetteImage->bindParam(':idOffre', $idOffre);
    $requetteImage->execute();
    $data = $requetteImage->fetchAll(PDO::FETCH_ASSOC);
    return ($data['lienPhoto']==$lien);
}

function mettreImageSurServeur( $ext,$img) {
    if (isset($img)) {     
        $dossier = "../img/";
        $extensions = $ext;
        $taille_maxi = 100000000;
        $fichier = microtime() . basename($img['name']);
        if (!isset($erreur)) { //S'il n'y a pas d'erreur, on upload
            //On formate le nom du fichier ici...
            $fichier = strtr($fichier, 'ÀÁÂÃÄÅÇÈÉÊËÌÍÎÏÒÓÔÕÖÙÚÛÜÝàáâãäåçèéêëìíîïðòóôõöùúûüýÿ', 'AAAAAACEEEEIIIIOOOOOUUUUYaaaaaaceeeeiiiioooooouuuuyy');
            $fichier = preg_replace('/([^.a-z0-9]+)/i', '-', $fichier);
            if (move_uploaded_file($img['tmp_name'], $dossier.$fichier)) { //Si la fonction renvoie TRUE, c'est que ça a fonctionné...              
                json_encode("Réussi");
                return $fichier;
            } else { //Sinon (la fonction renvoie FALSE).
                json_encode('Echec de l\'upload !');
                header("Status: HTTP/1.0 500 Internal Error");
            }
        } else {
            header("Status: HTTP/1.0 500 Internal Error");
            echo $erreur;
            
        }
    }
}
function verifieSiLaPosexiste($latitude,$longitude)
{
    if($requettePosExiste==NULL){
        $db = getDB();
        $requettePosExiste = $db->prepare("SELECT Position.idPosition FROM Position where latitude=:latitude AND longitude = :longitude");
        
    }
    $requettePosExiste->bindParam(':latitude',$latitude);
    $requettePosExiste->bindParam(':longitude',$longitude);
    $requettePosExiste->execute();
    $data = $requettePosExiste->fetchAll(PDO::FETCH_ASSOC);
    return $data;
}

function createOffre($lienPhoto,$description,$datePeremption,$idUtilisateur,$idType,$idPos){
    
    //mise en place d'un singleton pour gagner du temp sur les requette si elle ont deja été faites une fois
    if($requestOffre==NULL){
        $db = getDB();
        $requestOffre = $db->prepare("INSERT INTO `Offre`(`lienPhoto`, `description`, `datePeremption`, `idUtilisateur`, `idType`, `idPosition`)VALUES (:lienPhoto,:description,:datePeremption,:idUtilisateur,:idType,:idPosition)");
    }
    $requestOffre->bindParam(":lienPhoto",$lienPhoto);
    $requestOffre->bindParam(":description",$description);
    $requestOffre->bindParam(":datePeremption",$datePeremption);
    $requestOffre->bindParam(":idUtilisateur",$idUtilisateur);
    $requestOffre->bindParam(":idType",$idType);
    $requestOffre->bindParam(":idPosition",$idPos);  
    $requestOffre->execute();
    return json_encode(true);
}
function createPos($latitude,$longitude){
    if($requestPos == NULL){
        $db= getDB();
        $requestPos = $db->prepare("INSERT INTO `Position`(`latitude`, `longitude`) VALUES (:latitude,:longitude)");
    }
    $requestPos->bindParam(':latitude',$latitude);
    $requestPos->bindParam(':longitude',$longitude);
    $requestPos->execute();  
    $lastId = $db->lastInsertId();
    return $lastId;
}

function verifieSiLaPosEstPareille($idOffre,$latitude,$longitude)
{
    if($requettePosPareille==NULL){
        $db = getDB();
        $requettePosPareille = $db->prepare("SELECT Position.* FROM Position,Offre where Offre.idPosition=Position.idPosition AND idOffre=:idOffre");
        
    }
    $requettePosPareille->bindParam(':idOffre',$idOffre);
    $requettePosPareille->execute();
    $data = $requettePosPareille->fetchAll(PDO::FETCH_ASSOC);
    return ($data['latitude']==$latitude&&$data['longitude']==$longitude);
}

function getIdPos($idOffre){
    if($requetteGetPos==NULL){
        $db = getDB();
        $requetteGetPos= $db->prepare("SELECT idPosition FROM Offre WHERE idOffre = :idOffre");
    }
    $requetteGetPos->bindParam(':idOffre',$idOffre);
    $requetteGetPos->execute();
    $data = $requetteGetPos->fetchAll(PDO::FETCH_ASSOC);
    return $data['idPosition'];
}

function updateOffre($idOffre,$lienPhoto,$description,$datePeremption,$idUtilisateur,$idType,$idPosition){
    
    //mise en place d'un singleton pour gagner du temp sur les requette si elle ont deja été faites une fois
    
    if($requestUpdateOffre==NULL){
        $db = getDB();
        $requestUpdateOffre = $db->prepare("UPDATE `Offre` SET `lienPhoto`=:lienPhoto,`description`=:description,`datePeremption`=:datePeremption,`idUtilisateur`=:idUtilisateur,`idType`=:idType,`idPosition`=:idPosition WHERE idOffre=:idOffre");
    }
    $requestUpdateOffre->bindParam(':idOffre',$idOffre,PDO::PARAM_STR);
    $requestUpdateOffre->bindParam(':lienPhoto',$lienPhoto,PDO::PARAM_STR);
    $requestUpdateOffre->bindParam(':description',$description,PDO::PARAM_STR);
    $requestUpdateOffre->bindParam(':datePeremption',$datePeremption);
    $requestUpdateOffre->bindParam(':idUtilisateur',$idUtilisateur,PDO::PARAM_INT);
    $requestUpdateOffre->bindParam(':idType',$idType,PDO::PARAM_INT);
    $requestUpdateOffre->bindParam(':idPosition',$idPosition,PDO::PARAM_INT);  
    
    $requestUpdateOffre->execute(); 
    
    
}





