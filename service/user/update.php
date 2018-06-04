<?php
include '../functions.inc.php';
if(filter_has_var(INPUT_POST,"idUtilisateur")){
    ///numero de téléphone
    $numero = $_SERVER['PHP_AUTH_USER'];
    ///numero + salage en sha1
    $pass = $_SERVER['PHP_AUTH_PW'];
    if(login($numero, $pass)){
        $numero = filter_has_var(INPUT_POST, 'numero');
        $prenom = filter_has_var(INPUT_POST, 'prenom');
        $nom = filter_has_var(INPUT_POST, 'nom');
        $email = filter_has_var(INPUT_POST, 'email');
        $idUtilisateur = filter_has_var(INPUT_POST,'idUtilisateur');
        update($idUtilisateur,$nom,$prenom,$numero,$email);
    }
    
}

function update($idUtilisateur,$nom,$prenom,$numero,$email){
    $db = getDB();
    $request = $db->prepare("UPDATE `Utilisateur` SET `idUtilisateur`=[:idUtilisateur],`nom`=[:nom],`prenom`=[:prenom],`numero`=[:numero],`email`=[:email] WHERE idUtilisateur = :idUtilisateur");
    $request->bindParam(':email',$email,PDO::PARAM_STR);
    $request->bindParam(':nom',$nom,PDO::PARAM_STR);
    $request->bindParam(':prenom',$prenom,PDO::PARAM_STR);
    $request->bindParam(':numeros',$numero,PDO::PARAM_STR);
    $request->bindParam(':idUtilisateur',$idUtilisateur,PDO::PARAM_STR);
    $request->execute();    
    
    
}
