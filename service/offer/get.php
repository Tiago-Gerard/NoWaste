<?php
/*
	Projet:			NoWaste - Projet réalisé dans le cadre du TPI
	
	Page: 			index.php
	Description:            Retourne l'Utilisateur en fonction des infomation envoyer en header
	
	Auteur:			Tiago Gerard
*/

// Require du PDO
require "../pdo.php";
static $requestGet =NULL;
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
	public $lien;
	public $description;
	public $date;
	public $idType;
        public $distance;
        public $contact;
}

if(filter_has_var(INPUT_POST,"latitude")){
    
    $latitude = filter_input(INPUT_POST, 'latitude');
    $longitude = filter_input(INPUT_POST, 'longitude');
    
    echo getOffreProche($latitude,$longitude);
    
}



function getOffreProche($latitude,$longitude){
    if($requestGet==NULL){
        $db = getDB();
        //requette calculant les 20 offre les plus proches
        $requestGet = $db->prepare("SELECT (6378137 * (PI()/2 - ASIN( SIN((PI()*:lat /180)) * SIN((PI()*Position.latitude/180)) + COS((PI()*:long/180) -(PI()*Position.longitude/180)) * COS((PI()*Position.latitude/180)) *COS((PI()*:lat/180))))) as distance, Offre.* ,Utilisateur.numero from Position,Offre,Utilisateur where Position.idPosition = Offre.idPosition AND Utilisateur.idUtilisateur = Offre.idUtilisateur ORDER BY (6378137 * (PI()/2 - ASIN( SIN((PI()*:lat/180)) * SIN((PI()*Position.latitude/180)) + COS((PI()*:long/180) -(PI()*Position.longitude/180)) * COS((PI()*Position.latitude/180)) *COS((PI()*:lat)))))LIMIT 20");        
    }
    $requestGet->bindParam(':lat', $latitude,PDO::PARAM_LOB);
    $requestGet->bindParam(':long', $longitude,PDO::PARAM_LOB);
    $requestGet->execute();
    $data = $requestGet->fetchAll(PDO::FETCH_ASSOC);
    $array = array();
    //var_dump($data);
    foreach($data as $entry){
		$obj = new Offre();
                $obj->id =$entry['idOffre'];
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




