'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngRoute',
    'myApp.view1',
    'myApp.view2',
    'myApp.version'
])
    .config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
        $locationProvider.hashPrefix('!');

        $routeProvider.otherwise({redirectTo: '/view1'});
    }])
    .controller("homeController", ["$http", "$scope", function($http, $scope) {
        $scope.skits=[]  
        function getDocuments() {
            $http.get("http://localhost:3000/documents/searchDocuments", {params: {name:"Jason Sigman"}})
                .then(function(response) {
                    $scope.skits.splice(0, $scope.skits.length)
                    $scope.skits.push.apply($scope.skits, response.data.hits.hits);
                })
        }
        $scope.removeDocument = function(skit)  {
           $http.post("http://localhost:3000/documents/removeDocument", {id: skit._id})
            .then(function(response) { 
            getDocuments()
            })
        }
        $scope.addDocument = function() {
            console.log($scope.newSkit)
            $http.post("http://localhost:3000/documents/addDocument", {name: "Jason Sigman", content: $scope.newSkit})
                .then(function(response) {
                    $scope.newSkit = "";
                    setTimeout(function(){
                        getDocuments()
                    }, 5000);
                })
        }
        getDocuments()
        
    }])
