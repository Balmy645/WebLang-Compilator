<?php
include 'db.php';
$stmt = $pdo->query("SELECT * FROM productos");
while ($row = $stmt->fetch()) {
  print_r($row);
}
?>