(function(angular) {
    var app = angular.module('app');

    app.factory('TableService', function($http, $uibModal) {
        return {
            getDataForTable: function(tableName, callback) {
                var url = '/api/table/' + tableName;
                $http.get(url).then(function(result) {
                    callback(result.data);
                });
            },
            getColumnsForTable: function(tableName, callback) {
                var url = '/api/table/columns/' + tableName;

                $http.get(url).then(function(result) {
                    callback(result.data);
                });
            },
            insertIntoTable: function(tableName, data, callback) {
                var url = '/api/table/' + tableName;

                $http.post(url, data).then(function() {
                    callback();
                });
            },


            getLinkedTable: function(tableName, callback) {
                console.log(tableName + ' 111');
                var url = '/api/table/linkedTable/' + tableName;

                $http.get(url).then(function(result) {
                    callback(result);
                });
            },

            getPrimaryKeyName: function(tableName, callback) {
                var url = '/api/table/primaryKeyName/' + tableName;

                $http.get(url).then(function(result) {
                    callback(result);
                });
            },

            updateElementInTable: function(tableName, tableId, elementIdValue, data, callback) {
                var url = '/api/table/' + tableName + '/element/' + elementIdValue;

                $http.put(url, data).then(function(result) {
                    callback(result.data);
                });
            },
            searchTable: function(tableName, data, callback) {
                var url = '/api/table/' + tableName + '/search';

                $http.post(url, data).then(function(result) {
                    callback(result.data);
                });
            },
            deleteFromTable: function(tableName, elementId, data, callback, errorCallback) {
                var openErrorDialog = function(msg, callback) {
                    var modalInstance = $uibModal.open({
                        templateUrl: '/Templates/dialog.html',
                        controller: 'DialogController',
                        windowClass: 'xx-dialog',
                        resolve: {
                            error: function() {
                                return msg;
                            }
                        }
                    });

                    modalInstance.result.then(function(selectedItem) {
                        callback();
                    }, function() {

                    });
                };
                var url = '/api/table/' + tableName + '/element/' + elementId;
                console.log('URL: ' + url);
                $http.post(url, data).then(function(result) {
                    if (result.data !== '') {
                        openErrorDialog(result.data, callback);
                    }
                    console.log(result);
                    callback();
                }, function(error) {
                    console.log(error);
                    errorCallback(error);
                });

            },

            callIReports: function(value) {
                console.log(' USAO U CALLIREPORTS');
                var url = '/api/table/jasperReport/' + value;
                $http.get(url);
            },

            callIReportsDate: function(datumFrom, datumUntil) {
                var url = '/api/table/jasperReport/' + datumFrom + '/' + datumUntil;
                $http.get(url);
            },

            izveziXml: function(value) {
                var url = '/api/table/izveziXml/' + value;
                $http.get(url);
            },
            
            getJosOpisnihPolja : function(nazivPolja, vrednostPolja, callback){
            	 var url = '/api/table/josOpisnihPolja/' + nazivPolja + '/' + vrednostPolja;
            	 $http.get(url).then(function(result) {
                     callback(result);
                 });
            }


        };
    });

    app.service('TableOperations', function() {
        var that = this;

        that.refresh = {};
        that.gridOptionsApi = {};

        this.setRefreshMethod = function(refreshMethod) {
            that.refresh = refreshMethod;
        };
        this.setGridApi = function(gridApi) {
            that.gridOptionsApi = gridApi;
        };
        this.init = {};

    });
})(angular);
