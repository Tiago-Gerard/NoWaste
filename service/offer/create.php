<?php
/*
	Projet:			NoWaste - Projet réalisé dans le cadre du TPI
	
	Page: 			create.php
	Description:            Crée une offre avec les données envoyer en post
	
	Auteur:			Tiago Gerard
*/
//La requette preparée sous forme d'un singleton
static $request = NULL;
if(filter_has_var(INPUT_POST,"lienPhoto")){
     ///numero de téléphone
    $numero = $_SERVER['PHP_AUTH_USER'];
    ///numero + salage en sha1
    $pass = $_SERVER['PHP_AUTH_PW'];
    if(login($numero, $pass)){       
        $lienPhoto = filter_input(INPUT_POST, 'lienPhoto');
        $description = filter_input(INPUT_POST, 'description');
        $datePeremption = filter_input(INPUT_POST, 'datePetemption');
        $idUtilisateur = filter_input(INPUT_POST, 'idUtilisateur');
        $idType = filter_input(INPUT_POST, 'idType');
        $latitude = filter_input(INPUT_POST, 'latitude');
        $longitude = filter_input(INPUT_POST, 'longitude');
        create($nom,$prenom,$numero,$email);
    }
}

function create($lienPhoto,$description,$datePeremption,$idUtilisateur,$idType,$latitude,$longitude){
    //mise en place d'un singleton pour gagner du temp sur les requette si elle ont deja été faites une fois
    if($request == NULL){
        $db = getDB();
    $request = $db->prepare("INSERT INTO `Position`(`latitude`, `longitude`) VALUES (:latitude,:longitude)");
    }
    
    $request->bindParam(':latitude',$latitude,PDO::PARAM_STR);
    $request->bindParam(':longitude',$longitude,PDO::PARAM_STR);
    $request->execute();  
    
    $request = $db->prepare("INSERT INTO `Offre`(`lienPhoto`, `description`, `datePeremption`, `idUtilisateur`, `idType`, `idPosition`) "
            . "VALUES (:lienPhoto,:description,:datePeremption,:idUtilisateur,:idType,:idPosition)");
    $request->bindParam(':lienPhoto',$lienPhoto,PDO::PARAM_STR);
    $request->bindParam(':description',$description,PDO::PARAM_STR);
    $request->bindParam(':datePeremption',$datePeremption,PDO::PARAM_STR);
    $request->bindParam(':$idUtilisateur',$idUtilisateur,PDO::PARAM_STR);
    $request->bindParam(':idType',$idType,PDO::PARAM_STR);
    $request->bindParam(':idPosition',$db->lastInsertId(),PDO::PARAM_STR);
    
    
    $request->execute(); 
    
    
}

