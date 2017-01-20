angular.module('similarityApp', [])
  .controller('AppController', ['$scope', function($scope) {
    $scope.textRecos = null;
    $scope.proposedRecos = null;
    $scope.baseUrl="http://ec2-35-167-88-109.us-west-2.compute.amazonaws.com";

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
            url: $scope.baseUrl + '/equalJaccard',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            crossDomain: true,
            data: text,		
            success: $scope.updateJaccard
        });
        
        $.ajax({
            url: $scope.baseUrl + '/cosine',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            crossDomain: true,
            data: text,
            success: $scope.updateText
        });

    }
  }]);
