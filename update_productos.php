<?php
include 'db.php';
$id = $_POST['id'];
$sql = "UPDATE productos SET  WHERE id=?";
$stmt = $pdo->prepare($sql);
$stmt->bindValue(1, $id);
$stmt->execute();
echo "Actualizado";
?>