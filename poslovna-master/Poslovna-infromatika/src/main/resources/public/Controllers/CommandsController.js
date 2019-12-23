(function(angular) {
    var app = angular.module('app');

    var CommandsController = function($scope, StateService, stateMachine, SelectedService, $uibModal, TableService, $stateParams, TableOperations, SelectedService) {
        var state = StateService.getState();
        $scope.stateCurrent = StateService.stateCodeToText(state);

        $scope.safeApply = function(fn) {
            var phase = this.$root.$$phase;
            if (phase === '$apply' || phase === '$digest') {
                this.$eval(fn);
            } else {
                this.$apply(fn);
            }
        };

        var refreshState = function() {
            var state = StateService.getState();
            $scope.stateCurrent = StateService.stateCodeToText(state);
            console.log('novo stannje ' + $scope.stateCurrent);
            $scope.$evalAsync();
        };

        $scope.search = function() {
            var state = StateService.getState();
            var states = StateService.getStates();
            if (state === states.EDIT) {
                StateService.setState(states.SEARCH);
            } else if (state === states.ADD) {
                StateService.setState(states.SEARCH);

            }
            refreshState();
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
            SelectedService.clearSelection();
            $scope.$evalAsync();
            refreshState();
        };

        $scope.rollback = function() {
            var state = StateService.getState();
            var states = StateService.getStates();
            if (state === states.ADD) {
                StateService.setState(states.EDIT);
            } else if (state === states.SEARCH) {
                StateService.setState(states.EDIT);
            }
            refreshState();
        };
        $scope.deleteEntity = function() {
            var selectedRow = SelectedService.getSelectedRow();

            if (!selectedRow.length) {
                var modalInstance = $uibModal.open({
                    templateUrl: '/Templates/notSelectedDialog.html',
                    controller: 'dialogsCommandsController'
                });

                modalInstance.result.then(function(selectedItem) {
                    $scope.selected = selectedItem;
                }, function() {

                });
            } else {
                var modalInstance = $uibModal.open({
                    templateUrl: '/Templates/confirmDialog.html',
                    controller: 'dialogsCommandsController',
                    resolve: {
                        selectedRow: function() {
                            return selectedRow;
                        }
                    }
                });

                modalInstance.result.then(function(response) {
                    if (response === 'ok') {
                        var elementId = '';
                        for (var i = 0; selectedRow.length; i++) {
                            if (selectedRow[i].primaryKey) {
                                elementId = selectedRow[i].columnValue;
                                break;
                            }
                        }
                        TableService.deleteFromTable($stateParams.tableName, elementId, selectedRow,function() {
                            TableOperations.refresh();
                        }, function(error) {
                          console.log(error);
                        });
                    }
                }, function() {

                });
            }

        };

        $scope.refresh= function() {
          TableOperations.refresh();
        };
    };
    app.controller('commandsContoller', CommandsController);
})(angular);
