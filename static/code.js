angular.module('similarityApp', [])
  .controller('AppController', ['$scope', function($scope) {
    $scope.textRecos = null;
    $scope.proposedRecos = null;

    $scope.updateText = function updateText(results){
        console.log(results);
        $scope.textRecos = results;
        $scope.$apply();
    }

    $scope.updateJaccard = function updateJaccard(results){
        console.log(results);
        $scope.proposedRecos = results;
        $scope.$apply();
    };

    $scope.postRequests = function postRequests(){
        var text = document.getElementById("method_text").value;
        $.ajax({
            url: 'http://localhost:5000/equalJaccard',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            crossDomain: true,
            data: text,		
            success: $scope.updateJaccard
        });
        
        $.ajax({
            url: 'http://localhost:5000/cosine',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            crossDomain: true,
            data: text,
            success: $scope.updateText
        });

    }
  }]);
