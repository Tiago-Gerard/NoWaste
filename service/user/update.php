<?php

include '../functions.inc.php';



if(filter_has_var(INPUT_POST,"idUtilisateur")){
    ///numero de téléphone
    //$numero = $_SERVER['PHP_AUTH_USER'];
    ///numero + salage en sha1
    //$pass = $_SERVER['PHP_AUTH_PW'];
    //if(login($numero, $pass)){
    
    $prenom = filter_input(INPUT_POST, 'prenom',FILTER_SANITIZE_STRING);
    $nom = filter_input(INPUT_POST, 'nom',FILTER_SANITIZE_STRING);
    $email = filter_input(INPUT_POST, 'email',FILTER_SANITIZE_EMAIL);
        $idUtilisateur = filter_input(INPUT_POST,'idUtilisateur',FILTER_SANITIZE_NUMBER_INT);
        echo update($idUtilisateur,$nom,$prenom,$numero,$email);
    //}
    
}


