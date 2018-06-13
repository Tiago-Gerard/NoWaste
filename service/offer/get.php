<?php
/*
	Projet:			NoWaste - Projet réalisé dans le cadre du TPI
	
	Page: 			index.php
	Description:            Retourne l'Utilisateur en fonction des infomation envoyer en header
	
	Auteur:			Tiago Gerard
*/

// Require du PDO
require "../pdo.php";
//static $requestGet =NULL;
$requestGetOffer=NULL;
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
if(filter_has_var(INPUT_SERVER,'PHP_AUTH_USER')){
    ///numero de téléphone
    $user = $_SERVER['PHP_AUTH_USER'];
    ///numero + salage en sha1
    $pass = $_SERVER['PHP_AUTH_PW'];
    //afunction getMesOffre($user);

}
if(filter_has_var(INPUT_GET,"idUtilisateur")&&(filter_has_var(INPUT_GET,"latitude")==false)){
    $idUtilisateur = filter_input(INPUT_GET,'idUtilisateur');
    echo geMytOffer($idUtilisateur);
}
if(filter_has_var(INPUT_GET,"latitude")&&(filter_has_var(INPUT_GET,"idType")==false)){
    $latitude = filter_input(INPUT_GET, 'latitude');
    $longitude = filter_input(INPUT_GET, 'longitude');
    $idUtilisateur = filter_input(INPUT_GET,'idUtilisateur');
    
    echo getOffreProche($latitude,$longitude,$idUtilisateur);
    
}
if(filter_has_var(INPUT_GET,"idType")){
    $latitude = filter_input(INPUT_GET, 'latitude');
    $longitude = filter_input(INPUT_GET, 'longitude');
    $idUtilisateur = filter_input(INPUT_GET,'idUtilisateur');
    $idType = filter_input(INPUT_GET,'idType');
    
    echo getOffreProcheByType($latitude,$longitude,$idUtilisateur,$idType);
    
}

//Retourne une offre par son identifiant
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




