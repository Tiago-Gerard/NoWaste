<?php
/*
	Projet:			NoWaste - Projet réalisé dans le cadre du TPI
	
	Page: 			update.php
	Description:            Crée un nouvelle utilisateur avec les données envoyer en post
	
	Auteur:			Tiago Gerard
*/
if(filter_has_var(INPUT_POST,"numero")){
    $numero = filter_has_var(INPUT_POST, 'numero');
    $prenom = filter_has_var(INPUT_POST, 'prenom');
    $nom = filter_has_var(INPUT_POST, 'nom');
    $email = filter_has_var(INPUT_POST, 'email');
    update($nom,$prenom,$numero,$email);
    
}

function create($idUtilisateur,$nom,$prenom,$numero,$email){
    $db = getDB();
    $request = $db->prepare("INSERT INTO `Utilisateur`(`nom`, `prenom`, `numero`, `email`) VALUES (:nom,:prenom,:numero,:email)");
    $request->bindParam(':email',$email,PDO::PARAM_STR);
    $request->bindParam(':nom',$nom,PDO::PARAM_STR);
    $request->bindParam(':prenom',$prenom,PDO::PARAM_STR);
    $request->bindParam(':numeros',$numero,PDO::PARAM_STR);
    $request->execute();    
    
    
}

