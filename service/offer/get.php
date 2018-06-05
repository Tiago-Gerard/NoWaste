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
class Utilisateur{
        public $id;
	public $lien;
	public $description;
	public $date;
	public $type;
        public $distance;
        public $contact;
}
function getOffreProche($latitude,$longitude){
    if($requestGet){
        $db = getDB();
        //requette calculant les 20 offre les plus proches
        $requestGet = $db->prepare("select Position.idPosition "
                . ",(:long * (PI()/2 - ASIN( SIN((PI()*:lat/180)) * SIN((PI()*Position.latitude/180)) + COS((PI()*:long/180) -(PI()*Position.longitude/180)) * COS((PI()*Position.latitude/180)) *COS((PI()*:lat/180))))) as ditance,"
                . "Offre.*"
                . ",Utilisateur.numero "
                . "from Position,Offre,Utilisateur "
                . "where Position.idPosition = Offre.idPosition AND"
                . " Utilisateur.idUtilisateur = Offre.idUtilisateur "
                . "ORDER BY (6378137 * (PI()/2 - ASIN( SIN((PI()*:lat/180)) * SIN((PI()*Position.latitude/180)) + COS((PI()*:long/180) -(PI()*Position.longitude/180)) * COS((PI()*Position.latitude/180)) *COS((PI()*:lat)))))");        
    }
    $requestGet->bindParam(':lat', $latitude);
    $requestGet->bindParam(':lat', $latitude);
    $data = $requestGet->execute();
}




