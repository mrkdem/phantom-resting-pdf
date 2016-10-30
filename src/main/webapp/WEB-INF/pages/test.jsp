<!DOCTYPE HTML>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<body ng-app="">

<div ng-init="goToHtml = '/panda/html';goToPdf = '/panda/pdf';goToIndex = '/panda/'">
<p><a ng-href="{{goToHtml}}">Go to HTML</a></p>
<p><a ng-href="{{goToPdf}}">Go to Pdf</a></p>
<p><a ng-href="{{goToIndex}}">Go to index - chart</a></p>
</div>
<!--
<a href="/panda/html">Get HTML</a><br><br>
<a href="/panda/pdf">Get PDF</a><br><br>
<a href="/panda/">Go to index - chart</a>
-->
</body>
</html>