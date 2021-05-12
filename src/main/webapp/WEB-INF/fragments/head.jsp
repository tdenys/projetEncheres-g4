<head>
<meta charset="UTF-8">
<title>Projet Enchère</title>

<!-- BOOTSTRAP -->
  <link rel="stylesheet" href="css/styles.css?v=1.0">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
  <!-- Google Fonts -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap">
  <!-- Bootstrap core CSS -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
  <!-- Material Design Bootstrap -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.15.0/css/mdb.min.css" rel="stylesheet">

  <!-- JQuery -->
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <!-- Bootstrap tooltips -->
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.4/umd/popper.min.js"></script>
  <!-- Bootstrap core JavaScript -->
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.4.1/js/bootstrap.min.js"></script>
  <!-- MDB core JavaScript -->
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.15.0/js/mdb.min.js"></script>

	<style>
		footer i.fa-heart {
		  color: hsl(324, 70%, 45%);
		  animation: wheelHueColor 10s infinite;
		}
		nav i.fa-gavel {
			animation: hammerMove 5s infinite;
		}
		@keyframes wheelHueColor {
		  from, to { color: hsl(324, 70%, 45%); }
		  10%      { color: hsl(360, 65%, 45%); }
		  20%      { color: hsl( 36, 80%, 45%); }
		  30%      { color: hsl( 72, 75%, 35%); }
		  40%      { color: hsl(108, 65%, 35%); }
		  50%      { color: hsl(144, 75%, 35%); }
		  60%      { color: hsl(180, 75%, 35%); }
		  70%      { color: hsl(216, 60%, 45%); }
		  80%      { color: hsl(252, 65%, 50%); }
		  90%      { color: hsl(288, 60%, 40%); }
		}
		@keyframes hammerMove{
		  from, to { transform: rotate(0deg); }
		  20%      { transform: rotate(0deg); }
		  50%      { transform: rotate(43deg); }
		  80%      { transform: rotate(0deg); }
		  90%      { transform: rotate(0deg); }
		}
		body{
			background-image: url("https://source.unsplash.com/1920x1080/?wild");
			background-size: cover;
			margin-bottom: 100px;
			background-blend-mode: luminosity;
		}
		html {
			position: relative;
    		min-height: 100%;
		}
		footer {
			position: absolute;
		    bottom: 0;
		    width: 100%;
		}
	</style>

</head>