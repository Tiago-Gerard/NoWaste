<?php
/*
	Projet:			NoWaste - Projet réalisé dans le cadre du TPI
	
	Page: 			index.php
	Description:            Retourne l'Utilisateur en fonction des infomation envoyer en header
	
	Auteur:			Tiago Gerard
*/

// Require du PDO
require "../pdo.php";

//La requette preparée sous forme d'un singleton
static $requestGetUser=NULL;
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

// Retourne toutes les écoles en Json
function getUser($numero,$pwd){
    //mise en place d'un singleton pour gagner du temp sur les requette si elle ont deja été faites une fois
    if($requestGetUser){
        $db = getDB();
        $request = $db->prepare("SELECT * FROM `Utilisateur` WHERE numero=:numero");
    }
   
    $request->bindParam(':numero',$numero,PDO::PARAM_STR);
    $request->execute();    
    $data = $request->fetchAll(PDO::FETCH_ASSOC);
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
        if(sha1($obj->numero . "lapinfuta45")==$pwd)
            {
            return json_encode($array);
            }
        else{
            return json_encode("Access denied");
        }
    
}
///numero de téléphone
$user = $_SERVER['PHP_AUTH_USER'];
///numero + salage en sha1
$pass = $_SERVER['PHP_AUTH_PW'];

echo getEcoles($user,$pass);





