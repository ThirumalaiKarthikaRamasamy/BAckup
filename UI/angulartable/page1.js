 var app= angular.module("myApp",[]);
 app.controller("myCtrl",function($scope,$rootScope){
     
	 $scope.list = [
	 {
	 index : 1,
	 name : "Karthika",
	 email : "karthika@capgemini.com"
	 
	 }
	 
	 {
	 index : 1,
	 name : "Soumi",
	 email : "soumi@capgemini.com"
	 
	 }
	 
	 ];
       
 })