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
static $requetteGetPos=NULL;
static $requettePosExiste =NULL;
static $requestUpdateOffre=NULL;
static $requestDelete=NULL;
static $requestCreate=NULL;
static $requestUpdate=NULL;
static $requestGetUser=NULL;
static $requestGetOffer=NULL;
static $requestDelete=NULL;

/*
	Classe pour modéliser une école 
	
	id: 			L'id de l'offre
	lien: 			Lien de l'image
        description:            La déscription de l'offre
	date:			La date de peremption de l'offre
	type:			le Type de l'offre
 *      distance:               la distance de l'offre en km
 *      contact:                le contact de l'utilisateur email ou téléphone
*/
class Offre{
        public $id;
        public $prenom;
	public $lien;
	public $description;
	public $date;
	public $idType;
        public $distance;
        public $contact;
}

class MyOffre{
        public $id;
        public $prenom;
	public $lien;
	public $description;
	public $date;
	public $idType;
        public $latitude;
        public $longitude;
        public $contact;
}
/*
	Classe pour modéliser une école 
	
	id: 			L'id de l'utilisateur
	Nom: 			Nom de l'utilisateur
        Prenom:                 Prenom de l'utilisateur
	Numero:			Numero de l'utilisateur
	Email:			Email de l'utilisateur
*/	
class Utilisateur{
        public $idUtilisateur;
	public $nom;
	public $prenom;
	public $numero;
	public $email;
}
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
        $requettePosPareille = $db->prepare("SELECT Position.* FROM Position,Offre where Offre.idPosition=Position.idPosition AND idOffre=:idOffre LIMIT 1");
        
    }
    $requettePosPareille->bindParam(':idOffre',$idOffre);
    $requettePosPareille->execute();
    $data = $requettePosPareille->fetchAll(PDO::FETCH_ASSOC);
    return ($data[0]['latitude']==$latitude&&$data[0]['longitude']==$longitude);
}

function getIdPos($idOffre){
    if($requetteGetPos==NULL){
        $db = getDB();
        $requetteGetPos= $db->prepare("SELECT idPosition FROM Offre WHERE idOffre = :idOffre LIMIT 1");
    }
    $requetteGetPos->bindParam(':idOffre',$idOffre);
    $requetteGetPos->execute();
    $data = $requetteGetPos->fetchAll(PDO::FETCH_ASSOC);
    return $data[0]['idPosition'];
}

function updateOffre($idOffre,$description,$datePeremption,$idType,$idPosition){
    
    //mise en place d'un singleton pour gagner du temp sur les requette si elle ont deja été faites une fois
    
    if($requestUpdateOffre==NULL){
        $db = getDB();
        $requestUpdateOffre = $db->prepare("UPDATE `Offre` SET `description`=:description,`datePeremption`=:datePeremption,`idType`=:idType,`idPosition`=:idPosition WHERE idOffre=:idOffre");
    }
    $requestUpdateOffre->bindParam(':idOffre',$idOffre,PDO::PARAM_STR);
    $requestUpdateOffre->bindParam(':description',$description,PDO::PARAM_STR);
    $requestUpdateOffre->bindParam(':datePeremption',$datePeremption);
    $requestUpdateOffre->bindParam(':idType',$idType,PDO::PARAM_INT);
    $requestUpdateOffre->bindParam(':idPosition',$idPosition,PDO::PARAM_INT);  
    
    $requestUpdateOffre->execute(); 
    return json_encode(true);
    
}
function deleteOffre($idOffre){
    if($requestDelete == NULL){
        $db = getDB();
    $requestDelete = $db->prepare("DELETE FROM `Offre` WHERE Offre.idOffre=:idOffre");
    }
    
    $requestDelete->bindParam(':idOffre',$idOffre);
    $requestDelete->execute();
    return json_encode(true);
}
function geMytOffer($id){
    //mise en place d'un singleton pour gagner du temp sur les requette si elle ont deja été faites une fois
    if($requestGetOffer==NULL){
        $db = getDB();
        $requestGetOffer = $db->prepare("SELECT Utilisateur.prenom,Offre.* ,Position.latitude,Position.longitude,Utilisateur.numero FROM Position,Offre,Utilisateur WHERE Offre.idUtilisateur=:idUtilisateur AND Offre.idUtilisateur=Utilisateur.idUtilisateur AND Position.idPosition = Offre.idPosition");
    }    
    $requestGetOffer->bindParam(':idUtilisateur',$id);
    $requestGetOffer->execute();
    $data = $requestGetOffer->fetchAll(PDO::FETCH_ASSOC);
    $array = array();
    foreach($data as $entry){
		$obj = new MyOffre();
                $obj->id =$entry['idOffre'];
                $obj->prenom = $entry['prenom'];
		$obj->lien = $entry['lienPhoto'];
		$obj->description = $entry['description'];
		$obj->date =$entry['datePeremption'];
		$obj->idType =$entry['idType'];
                $obj->latitude = $entry['latitude'];
                $obj->longitude = $entry['longitude'];
                $obj->contact = $entry['numero'];
		$array[] = $obj;

	}
        return json_encode($array);
}
function getOffreProcheByType($latitude,$longitude,$idUtilisateur,$idType){
    //if($requestGet==NULL){
        $db = getDB();
        //requette calculant les 20 offre les plus proches
        $requestGet = $db->prepare("SELECT Utilisateur.prenom ,(6378137 * (PI()/2 - ASIN( SIN((PI()*:lat /180)) * SIN((PI()*Position.latitude/180)) + COS((PI()*:long/180) -(PI()*Position.longitude/180)) * COS((PI()*Position.latitude/180)) *COS((PI()*:lat/180))))) as distance, Offre.* ,Utilisateur.numero from Position,Offre,Utilisateur where Position.idPosition = Offre.idPosition AND Utilisateur.idUtilisateur = Offre.idUtilisateur AND Utilisateur.idUtilisateur != :idUtilisateur AND Offre.idType = :idType ORDER BY (6378137 * (PI()/2 - ASIN( SIN((PI()*:lat /180)) * SIN((PI()*Position.latitude/180)) + COS((PI()*:long/180) -(PI()*Position.longitude/180)) * COS((PI()*Position.latitude/180)) *COS((PI()*:lat/180)))))LIMIT 20");        
    //}
    $requestGet->bindParam(':lat', $latitude,PDO::PARAM_LOB);
    $requestGet->bindParam(':long', $longitude,PDO::PARAM_LOB);
    $requestGet->bindParam(':idUtilisateur', $idUtilisateur);
    $requestGet->bindParam(':idType',$idType,PDO::PARAM_INT);
    $requestGet->execute();
    $data = $requestGet->fetchAll(PDO::FETCH_ASSOC);
    $array = array();
    foreach($data as $entry){
		$obj = new Offre();
                $obj->id =$entry['idOffre'];
                $obj->prenom = $entry['prenom'];
		$obj->lien = $entry['lienPhoto'];
		$obj->description = $entry['description'];
		$obj->date =$entry['datePeremption'];
		$obj->idType =$entry['idType'];
                $obj->distance = $entry['distance'];
                $obj->contact = $entry['numero'];
		$array[] = $obj;

	}
        return json_encode($array);
}
function getOffreProche($latitude,$longitude,$idUtilisateur){
    //if($requestGet==NULL){
        $db = getDB();
        //requette calculant les 20 offre les plus proches
        $requestGet = $db->prepare("SELECT Utilisateur.prenom ,(6378137 * (PI()/2 - ASIN( SIN((PI()*:lat /180)) * SIN((PI()*Position.latitude/180)) + COS((PI()*:long/180) -(PI()*Position.longitude/180)) * COS((PI()*Position.latitude/180)) *COS((PI()*:lat/180))))) as distance, Offre.* ,Utilisateur.numero from Position,Offre,Utilisateur where Position.idPosition = Offre.idPosition AND Utilisateur.idUtilisateur = Offre.idUtilisateur AND Utilisateur.idUtilisateur != :idUtilisateur ORDER BY (6378137 * (PI()/2 - ASIN( SIN((PI()*:lat /180)) * SIN((PI()*Position.latitude/180)) + COS((PI()*:long/180) -(PI()*Position.longitude/180)) * COS((PI()*Position.latitude/180)) *COS((PI()*:lat/180)))))LIMIT 20");        
    //}
    $requestGet->bindParam(':lat', $latitude,PDO::PARAM_LOB);
    $requestGet->bindParam(':long', $longitude,PDO::PARAM_LOB);
    $requestGet->bindParam(':idUtilisateur', $idUtilisateur);
    $requestGet->execute();
    $data = $requestGet->fetchAll(PDO::FETCH_ASSOC);
    $array = array();
    foreach($data as $entry){
		$obj = new Offre();
                $obj->id =$entry['idOffre'];
                $obj->prenom = $entry['prenom'];
		$obj->lien = $entry['lienPhoto'];
		$obj->description = $entry['description'];
		$obj->date =$entry['datePeremption'];
		$obj->idType =$entry['idType'];
                $obj->distance = $entry['distance'];
                $obj->contact = $entry['numero'];
		$array[] = $obj;

	}
        return json_encode($array);
}
function getTypes(){
    //mise en place d'un singleton pour gagner du temp sur les requette si elle ont deja été faites une fois
    if($requestGetType==NULL){
        $db = getDB();
        $requestGetType = $db->prepare("SELECT * FROM `Type`");
    }   
    $requestGetType->execute();
    
    $data = $requestGetType->fetchAll(PDO::FETCH_ASSOC);
    return json_encode($data);
}

// Retourne toutes l'utilisateur en Json
function getUser($numero,$pwd){
    //mise en place d'un singleton pour gagner du temp sur les requette si elle ont deja été faites une fois
    if($requestGetUser==NULL){
        $db = getDB();
        $requestGetUser = $db->prepare("SELECT * FROM `Utilisateur` WHERE numero=:numero");
    }
   
    $requestGetUser->bindParam(':numero',$numero,PDO::PARAM_STR);
    $requestGetUser->execute();    
    $data = $requestGetUser->fetchAll(PDO::FETCH_ASSOC);
	$array = array();
	foreach($data as $entry){
		$obj = new Utilisateur();
                $obj->idUtilisateur =$entry['idUtilisateur'];
		$obj->nom = $entry['nom'];
		$obj->prenom = $entry['prenom'];
		$obj->numero =$entry['numero'];
		$obj->email =$entry['email'];
		$array[] = $obj;
	}
            return json_encode($array);  
}
function update($idUtilisateur,$nom,$prenom,$numero,$email){
    if($requestUpdate==NULL){
        $db = getDB();
        $requestUpdate = $db->prepare("UPDATE `Utilisateur` SET `nom`=:nom,`prenom`=:prenom,`email`=:email WHERE `idUtilisateur` = :idUtilisateur");   
    }
    $requestUpdate->bindParam(':email',$email,PDO::PARAM_STR);
    $requestUpdate->bindParam(':nom',$nom,PDO::PARAM_STR);
    $requestUpdate->bindParam(':prenom',$prenom,PDO::PARAM_STR);
    $requestUpdate->bindParam(':idUtilisateur',$idUtilisateur,PDO::PARAM_INT);
    $requestUpdate->execute();    
    return json_encode(true);
    
}
function createUser($nom,$prenom,$numero,$email){
    //mise en place d'un singleton pour gagner du temp sur les requette si elle ont deja été faites une fois
    if($requestCreate==NULL){
       $db = getDB();
       $requestCreate = $db->prepare("INSERT INTO `Utilisateur`(`nom`, `prenom`, `numero`, `email`) VALUES (:nom,:prenom,:numero,:email)");     
    }
    $requestCreate->bindParam(':email',$email,PDO::PARAM_STR);
    $requestCreate->bindParam(':nom',$nom,PDO::PARAM_STR);
    $requestCreate->bindParam(':prenom',$prenom,PDO::PARAM_STR);
    $requestCreate->bindParam(':numero',$numero,PDO::PARAM_STR);
    $requestCreate->execute();   
}





