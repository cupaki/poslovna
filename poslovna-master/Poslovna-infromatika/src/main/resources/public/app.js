(function(angular) {
    agGrid.initialiseAgGridWithAngular1(angular);
    var app = angular.module('app', ['ui.router', 'agGrid', 'ngMaterial', 'ui.bootstrap', 'FSM']);

    app.config(['stateMachineProvider', function(stateMachineProvider) {
        stateMachineProvider.config({
            init: { // This is the initial state(the not_logged_in one, but you have to call it 'init'). It is mandatory.
                transitions: {
                    0: 'ADD',
                    1: 'EDIT',
                    2: 'SEARCH'
                }
            },
            ADD: {
                action : ['StateService', function(StateSerice){
                  StateSerice.setState(0);
                }]
            },
            EDIT: {
              action : ['StateService', function(StateSerice){
                StateSerice.setState(1);
              }]
            },
            SEARCH: {
              action : ['StateService', function(StateSerice){
                StateSerice.setState(2);
              }]
            }
        });
    }]);

    app.run(['stateMachine', function(stateMachine) {
        stateMachine.initialize();
    }]);





})(angular);
