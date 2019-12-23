(function(angular) {

    var app = angular.module('app');

    var dialogsCommandsController = function($scope, $uibModalInstance) {
        $scope.close = function() {
            $uibModalInstance.close('close');
        };
        $scope.ok = function() {
          $uibModalInstance.close('ok');
        };
    };

    app.controller('dialogsCommandsController', dialogsCommandsController);
})(angular);
