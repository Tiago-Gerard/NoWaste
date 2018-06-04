<?php
/*
	Projet:			NoWaste - Projet réalisé dans le cadre du TPI
	
	Page: 			update.php
	Description:            Modifie une offre avec les données envoyer en post
	
	Auteur:			Tiago Gerard
*/

include '../functions.inc.php';
if(filter_has_var(INPUT_POST,"idOffre")){
    ///numero de téléphone
    $numero = $_SERVER['PHP_AUTH_USER'];
    ///numero + salage en sha1
    $pass = $_SERVER['PHP_AUTH_PW'];
    if(login($numero, $pass)){
        
        $idUtilisateur = filter_has_var(INPUT_POST,'idOffre');
        $idUtilisateur = filter_has_var(INPUT_POST,'lienPhoto');
        $idUtilisateur = filter_has_var(INPUT_POST,'description');
        $idUtilisateur = filter_has_var(INPUT_POST,'datePeremption');
        $idUtilisateur = filter_has_var(INPUT_POST,'idUtilisateur');
        $idUtilisateur = filter_has_var(INPUT_POST,'Type');
        $idUtilisateur = filter_has_var(INPUT_POST,'latitude');
        update($idUtilisateur,$nom,$prenom,$numero,$email);
    }
    
}
static $requestUpdate=NULL;
if($requestUpdate=null){
    $requestUpdate = $db->prepare("UPDATE `Utilisateur` SET `idUtilisateur`=[:idUtilisateur],`nom`=[:nom],`prenom`=[:prenom],`numero`=[:numero],`email`=[:email] WHERE idUtilisateur = :idUtilisateur");
}

function update($idUtilisateur,$nom,$prenom,$numero,$email){
    $db = getDB();
    
    $request->bindParam(':email',$email,PDO::PARAM_STR);
    $request->bindParam(':nom',$nom,PDO::PARAM_STR);
    $request->bindParam(':prenom',$prenom,PDO::PARAM_STR);
    $request->bindParam(':numeros',$numero,PDO::PARAM_STR);
    $request->bindParam(':idUtilisateur',$idUtilisateur,PDO::PARAM_STR);
    $request->execute();    
    
    
}

