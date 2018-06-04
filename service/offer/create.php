<?php
if(filter_has_var(INPUT_POST,"numero")){
     ///numero de téléphone
    $numero = $_SERVER['PHP_AUTH_USER'];
    ///numero + salage en sha1
    $pass = $_SERVER['PHP_AUTH_PW'];
    if(login($numero, $pass)){       
        $lienPhoto = filter_has_var(INPUT_POST, 'lienPhoto');
        $description = filter_has_var(INPUT_POST, 'description');
        $datePeremption = filter_has_var(INPUT_POST, 'datePetemption');
        $idUtilisateur = filter_has_var(INPUT_POST, 'idUtilisateur');
        $idType = filter_has_var(INPUT_POST, 'idType');
        $latitude = filter_has_var(INPUT_POST, 'latitude');
        update($nom,$prenom,$numero,$email);
    }
}

function create($lienPhoto,$description,$datePeremption,$idUtilisateur,$idType,$latitude,$longitude){
    $db = getDB();
    $request = $db->prepare("INSERT INTO `Position`(`latitude`, `longitude`) VALUES (:latitude,:longitude)");
    $request->bindParam(':latitude',$latitude,PDO::PARAM_STR);
    $request->bindParam(':longitude',$longitude,PDO::PARAM_STR);
    $request->execute();  
    
    $request = $db->prepare("INSERT INTO `Offre`(`lienPhoto`, `description`, `datePeremption`, `idUtilisateur`, `idType`, `idPosition`) "
            . "VALUES (:lienPhoto,:description,datePeremption,idUtilisateur,idType,idPosition)");
    $request->bindParam(':lienPhoto',$lienPhoto,PDO::PARAM_STR);
    $request->bindParam(':description',$description,PDO::PARAM_STR);
    $request->bindParam(':datePeremption',$datePeremption,PDO::PARAM_STR);
    $request->bindParam(':$idUtilisateur',$idUtilisateur,PDO::PARAM_STR);
    $request->bindParam(':idType',$idType,PDO::PARAM_STR);
    $request->bindParam(':idPosition',$db->lastInsertId(),PDO::PARAM_STR);
    
    
    $request->execute(); 
    
    
}

