(function(angular) {
  var app = angular.module('app');

  var ErrorContoller = function($scope, error, $uibModalInstance) {
    $scope.error = error;

    $scope.ok = function() {
      $uibModalInstance.close('ok');
    };
  };

  app.controller('ErrorContoller', ErrorContoller);
})(angular);
