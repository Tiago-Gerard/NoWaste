<?php
/*
	Projet:			NoWaste - Projet réalisé dans le cadre du TPI
	
	Page: 			update.php
	Description:            Modifie une offre avec les données envoyer en post
	
	Auteur:			Tiago Gerard
*/

include '../functions.inc.php';
//La requette preparée sous forme d'un singleton
static $requestUpdate=NULL;
if(filter_has_var(INPUT_POST,"idOffre")){
    ///numero de téléphone
    //$numero = $_SERVER['PHP_AUTH_USER'];
    ///numero + salage en sha1
    //$pass = $_SERVER['PHP_AUTH_PW'];
    //if(login($numero, $pass)){
        
        $idUtilisateur = filter_input(INPUT_POST,'idOffre');
        $lienPhoto = filter_input(INPUT_POST,'lienPhoto');
        $description = filter_input(INPUT_POST,'description');
        $datePeremption = filter_input(INPUT_POST,'datePeremption');
        $idUtilisateur = filter_input(INPUT_POST,'idUtilisateur');
        $idType = filter_input(INPUT_POST,'idType');
        $latitude = filter_input(INPUT_POST,'latitude');
        $longitude = filter_input(INPUT_POST,'longitude');
        
        update($nom,$prenom,$numero,$email);
    //}
    
}



function update($lienPhoto,$description,$datePeremption,$idType,$idPosition){
    //mise en place d'un singleton pour gagner du temp sur les requette si elle ont deja été faites une fois
    if($requestUpdate=null){
    $db = getDB();
    $requestUpdate = $db->prepare("UPDATE `Offre` "
            . "SET `lienPhoto`=:lienPhoto,`description`=:description,`datePeremption`=:datePeremption,`idType`=:idType,`idPosition`=:idPosition "
            . "WHERE `idOffre`=:idOffre");
}
    
    $requestUpdate->bindParam(':lienPhoto',$lienPhoto,PDO::PARAM_STR);
    $requestUpdate->bindParam(':description',$description,PDO::PARAM_STR);
    $requestUpdate->bindParam(':datePeremption',$datePeremption,PDO::PARAM_STR);
    $requestUpdate->bindParam(':idType',$idType,PDO::PARAM_INT);
    $requestUpdate->bindParam(':idPosition',$idPosition,PDO::PARAM_INT);
    $requestUpdate->execute();       
}

