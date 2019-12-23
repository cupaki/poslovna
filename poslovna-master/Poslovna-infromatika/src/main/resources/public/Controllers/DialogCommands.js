(function(angular) {
    var app = angular.module('app');

    var DialogCommandsController = function($scope, StateService, stateMachine, $uibModal, TableService, $stateParams, TableOperations, SelectedService,UtilsService) {
        var state = StateService.getState();
        $scope.stateCurrent = StateService.stateCodeToText(state);


        var refreshState = function() {
            var state = StateService.getState();
            $scope.stateCurrent = StateService.stateCodeToText(state);
            console.log('novo stannje ' + $scope.stateCurrent);
            $scope.$evalAsync();
        };

        $scope.capitalize = function(string) {
          return UtilsService.capitalize(string);
        };

        $scope.add = function() {
            var state = StateService.getState();
            var states = StateService.getStates();
            if (state === states.EDIT) {
                StateService.setState(states.ADD);
            } else if (state === states.SEARCH) {
                StateService.setState(states.ADD);
            }
            TableOperations.gridOptionsApi.clearSelection = true;
            SelectedService.clearDialogSelection();
            $scope.$evalAsync();
            refreshState();
        };







        $scope.refresh= function() {
          TableOperations.refresh();
        };
    };
    app.controller('DialogCommandsController', DialogCommandsController);
})(angular);
