(function(angular) {

    var app = angular.module('app');

    app.service('StateService', function() {
        var states = {
            ADD: 0,
            EDIT: 1,
            SEARCH: 2
        };
        var state = states.EDIT;

        this.getState = function() {
            return state;
        };
        this.setState = function(st) {
            state = st;
        };
        this.getStates = function() {
            return states;
        };
        this.stateCodeToText = function(code) {
            switch (code) {
                case 0:
                    return 'Dodavanje';
                case 1:
                    return 'Pregled/Izmena';
                case 2:
                    return 'Pretraga';
            }
        };
    });
})(angular);
