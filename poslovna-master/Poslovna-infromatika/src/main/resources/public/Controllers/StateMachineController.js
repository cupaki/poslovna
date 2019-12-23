(function(angular) {
    var app = angular.module('app');

    var StateMachineContoller = function(stateMachineProvider) {

    };

    app.run(['stateMachine', function(stateMachine) {
        stateMachine.initialize();
    }]);
})(angular);
