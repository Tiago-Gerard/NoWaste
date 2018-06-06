<?php

require'../pdo.php';

//include '../functions.inc.php';

static $requestUpdate=null;
if(filter_has_var(INPUT_POST,"idUtilisateur")){
    ///numero de téléphone
    //$numero = $_SERVER['PHP_AUTH_USER'];
    ///numero + salage en sha1
    //$pass = $_SERVER['PHP_AUTH_PW'];
    //if(login($numero, $pass)){
         $numero = filter_input(INPUT_POST, 'numero',FILTER_SANITIZE_STRING);
    $prenom = filter_input(INPUT_POST, 'prenom',FILTER_SANITIZE_STRING);
    $nom = filter_input(INPUT_POST, 'nom',FILTER_SANITIZE_STRING);
    $email = filter_input(INPUT_POST, 'email',FILTER_SANITIZE_EMAIL);
        $idUtilisateur = filter_input(INPUT_POST,'idUtilisateur',FILTER_SANITIZE_NUMBER_INT);
        update($idUtilisateur,$nom,$prenom,$numero,$email);
    //}
    
}

function update($idUtilisateur,$nom,$prenom,$numero,$email){
    if($requestUpdate==NULL){
        $db = getDB();
        $requestUpdate = $db->prepare("UPDATE `Utilisateur` SET `nom`=:nom,`prenom`=:prenom,`numero`=:numero,`email`=:email WHERE `idUtilisateur` = :idUtilisateur");   
    }
    $requestUpdate->bindParam(':email',$email,PDO::PARAM_STR);
    $requestUpdate->bindParam(':nom',$nom,PDO::PARAM_STR);
    $requestUpdate->bindParam(':prenom',$prenom,PDO::PARAM_STR);
    $requestUpdate->bindParam(':numero',$numero,PDO::PARAM_STR);
    $requestUpdate->bindParam(':idUtilisateur',$idUtilisateur,PDO::PARAM_INT);
    $requestUpdate->execute();    
    
    
}
