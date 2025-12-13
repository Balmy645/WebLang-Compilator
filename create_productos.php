<?php
include 'db.php';

$sql = "INSERT INTO productos () VALUES ()";
$stmt = $pdo->prepare($sql);
$stmt->execute();
echo "Registro insertado";
?>