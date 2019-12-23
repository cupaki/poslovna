(function(angular) {
    var app = angular.module('app');



    var tableController = function($scope, TableService, $stateParams, $uibModal, $log, StateService, SelectedService, TableOperations, UtilsService, PickUpService) {

        var nazivTabeleBaza = $stateParams.tableName;
        var r = nazivTabeleBaza.replace(' ', '_');
        var n = r.toUpperCase();

        TableService.getLinkedTable(n, function(result) {
            $scope.linkedList = result.data;
        });

        $scope.state = StateService.getState();
        var states = StateService.getStates();
        $scope.insertingValue = [];
        var initData = function() {
            $scope.selectedRowData = [];
            for (var i = 0; i < $scope.insertingValue.length; i++) {
                $scope.selectedRowData.push({
                    columnName: $scope.insertingValue[i].columnName,
                    columnValue: '',
                    columnType: $scope.insertingValue[i].columnType,
                    primaryKey: $scope.insertingValue[i].primaryKey,
                    forgienKey: $scope.insertingValue[i].forgienKey,
                    lookUp: $scope.insertingValue[i].lookUp
                });
            }
        };

        TableOperations.init = initData;
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
                $log.info('Modal dismissed at: ' + new Date());


            });
        };
        $scope.tableName = $stateParams.tableName;
        $scope.open = function(tableName) {
            var nazivTabeleBaza2 = tableName;
            var r2 = nazivTabeleBaza2.replace(' ', '_');
            var n2 = r2.toUpperCase();
            var modalInstance = $uibModal.open({
                templateUrl: '/Templates/dialog.html',
                controller: 'DialogController',
                windowClass: 'xx-dialog',
                resolve: {
                    tableName: function() {
                        return n2;
                    }
                }
            });

            modalInstance.result.then(function(selectedItem) {
                $scope.selected = selectedItem;
                console.log('nakon init');
                console.log($scope.selectedRowData);
                // postavljanje pickUp vrednosti na ng-model
                var vrednostKolone = PickUpService.getVrednost();
                var nazivKolone = PickUpService.getColumn();
                var lookUpcolumn = PickUpService.getLookColumn();
                var lookUpValue = PickUpService.getLookUpValue();
                var skracenica = PickUpService.getSkracenica();
                var idJedMere = PickUpService.getIdJedMere();
                console.log('skracenica je ' + skracenica);
                console.log('id je ' + idJedMere);

                for (var i = 0; i < $scope.selectedRowData.length; i++) {
                    if ($scope.tableName === 'Stavke fakture' && (i === 6 || i === 9)) {
                      $scope.selectedRowData[6].columnValue = skracenica;
                      $scope.selectedRowData[9].columnValue = idJedMere;
                    } else if (i === 11 && $scope.tableName === 'Stavke fakture') {
                      console.log('stopa jeej ' + PickUpService.getStopaPdv());
                      $scope.selectedRowData[11].columnValue = PickUpService.getStopaPdv();
                    } else {
                      if ($scope.selectedRowData[i].columnName == nazivKolone) {
                          $scope.selectedRowData[i].columnValue = vrednostKolone;
                      }
                      if ($scope.selectedRowData[i].columnName == lookUpcolumn) {
                          $scope.selectedRowData[i].columnValue = lookUpValue;
                      }
                    }


                }
                $scope.iznosPDVa();



                //if(tableName === 'STAVKA_FAKTURE' || tableName === 'Stavka fakture'){

                //}
            }, function() {
                $log.info('Modal dismissed at: ' + new Date());


            });
        };

        var columnDefs = [];

        var gridOptions = {
            columnDefs: columnDefs,
            rowSelection: 'multiple',
            enableColResize: true,
            onSelectionChanged: onSelectionChanged
        };

        var sizeToFit = function() {
            gridOptions.api.sizeColumnsToFit();
        };

        $scope.selectedRowData = [];
        $scope.selectedRow = [];

        function onSelectionChanged() {
            $scope.selectedRowData = [];
            $scope.selectedRow = [];
            var selectedRows = gridOptions.api.getSelectedRows();
            var selectedRowsString = '';

            for (var i = 0; i < $scope.insertingValue.length; i++) {
                if ($scope.insertingValue[i].columnType !== 'datetime') {
                    var obj = selectedRows[0][$scope.insertingValue[i].columnName];
                    $scope.selectedRowData.push({
                        columnName: $scope.insertingValue[i].columnName,
                        columnValue: obj,
                        columnType: $scope.insertingValue[i].columnType,
                        primaryKey: $scope.insertingValue[i].primaryKey,
                        forgienKey: $scope.insertingValue[i].forgienKey,
                        lookUp: $scope.insertingValue[i].lookUp
                    });
                    $scope.selectedRow.push({
                        columnName: $scope.insertingValue[i].columnName,
                        columnValue: obj,
                        columnType: $scope.insertingValue[i].columnType,
                        primaryKey: $scope.insertingValue[i].primaryKey,
                        forgienKey: $scope.insertingValue[i].forgienKey,
                        lookUp: $scope.insertingValue[i].lookUp
                    });
                } else {
                    var date = new Date(selectedRows[0][$scope.insertingValue[i].columnName]);
                    $scope.selectedRowData.push({
                        columnName: $scope.insertingValue[i].columnName,
                        columnValue: date,
                        columnType: $scope.insertingValue[i].columnType,
                        primaryKey: $scope.insertingValue[i].primaryKey,
                        forgienKey: $scope.insertingValue[i].forgienKey,
                        lookUp: $scope.insertingValue[i].lookUp
                    });
                    $scope.selectedRow.push({
                        columnName: $scope.insertingValue[i].columnName,
                        columnValue: date,
                        columnType: $scope.insertingValue[i].columnType,
                        primaryKey: $scope.insertingValue[i].primaryKey,
                        forgienKey: $scope.insertingValue[i].forgienKey,
                        lookUp: $scope.insertingValue[i].lookUp
                    });
                }

            }
            SelectedService.setSelectedRow($scope.selectedRowData);
            // $scope.$apply();
            $scope.$evalAsync();
        }

        var clearSelection = function() {
            $scope.selectedRowData = [];
            initData();
        };
        SelectedService.clearSelection = clearSelection;
        var url = '/api/table/' + $stateParams.tableName;
        var refreshTable = function() {
            TableService.getDataForTable($stateParams.tableName, function(tableData) {
                console.log('refres');
                var rows = [];
                for (var i = 0; i < tableData.length; i++) {
                    var row = {};
                    for (var j = 0; j < $scope.insertingValue.length; j++) {
                        if ($scope.insertingValue[j].lookupField === null) {
                            row[$scope.insertingValue[j].columnName] = tableData[i][j];
                        } else {
                            row[$scope.insertingValue[j].columnName] = tableData[i][j];
                            row[$scope.insertingValue[j].lookupField.name] = tableData[i][j];
                        }
                    }
                    rows.push(row);
                }

                gridOptions.api.setRowData(rows);
                sizeToFit();
            });
            onRefreshAll();
        };
        var loadTable = function() {
            angular.element(document).ready(function() {
                TableService.getColumnsForTable($stateParams.tableName, function(tableColumns) {


                    $scope.tableColumns = tableColumns;
                    for (var i = 0; i < tableColumns.length; i++) {
                        columnDefs.push({
                            headerName: UtilsService.capitalize(tableColumns[i].name),
                            field: tableColumns[i].name
                        });
                        if (tableColumns[i].lookupField !== null) {
                            columnDefs.push({
                                headerName: UtilsService.capitalize(tableColumns[i].lookupField.name),
                                field: tableColumns[i].lookupField.name
                            });
                        }
                        $scope.insertingValue.push({
                            columnName: tableColumns[i].name,
                            columnType: tableColumns[i].type,
                            columnValue: '',
                            tableId: tableColumns[i].tableColumnId,
                            primaryKey: tableColumns[i].primaryKey,
                            forgienKey: tableColumns[i].forgienKey,
                            lookupField: tableColumns[i].lookupField,
                            fkTable: tableColumns[i].fkTable,
                            lookUp: tableColumns[i].lookUp
                        });
                        if (tableColumns[i].lookupField !== null) {
                            $scope.insertingValue.push({
                                columnName: tableColumns[i].lookupField.name,
                                columnType: tableColumns[i].lookupField.type,
                                columnValue: '',
                                tableId: tableColumns[i].lookupField.tableColumnId,
                                primaryKey: tableColumns[i].lookupField.primaryKey,
                                forgienKey: tableColumns[i].lookupField.forgienKey,
                                lookupField: tableColumns[i].lookupField.lookupField,
                                fkTable: tableColumns[i].fkTable,
                                lookUp: tableColumns[i].lookupField.lookUp
                            });
                        }

                        $scope.selectedRowData.push({
                            columnName: tableColumns[i].name,
                            columnType: tableColumns[i].type,
                            columnValue: '',
                            tableId: tableColumns[i].tableColumnId,
                            primaryKey: tableColumns[i].primaryKey,
                            forgienKey: tableColumns[i].forgienKey,
                            lookupField: tableColumns[i].lookupField,
                            lookUp: tableColumns[i].lookUp
                        });
                        if (tableColumns[i].lookupField !== null) {
                            $scope.selectedRowData.push({
                                columnName: tableColumns[i].lookupField.name,
                                columnType: tableColumns[i].lookupField.type,
                                columnValue: '',
                                tableId: tableColumns[i].lookupField.tableColumnId,
                                primaryKey: tableColumns[i].lookupField.primaryKey,
                                forgienKey: tableColumns[i].lookupField.forgienKey,
                                lookupField: tableColumns[i].lookupField.lookupField,
                                lookUp: tableColumns[i].lookupField.lookUp
                            });
                        }
                    }

                    var gridDiv = document.querySelector('#myGrid');
                    new agGrid.Grid(gridDiv, gridOptions);
                    TableService.getDataForTable($stateParams.tableName, function(tableData) {

                        var listaVrednostiIdevaFakture = [];

                        var rows = [];
                        for (var i = 0; i < tableData.length; i++) {
                            var row = {};
                            for (var j = 0; j < $scope.insertingValue.length; j++) {
                                if ($scope.insertingValue[j].lookupField === null) {
                                    if (j == 0) {
                                        if ($stateParams.tableName == 'Faktura' || $stateParams.tableName == 'FAKTURA') {
                                            console.log(tableData[i][0] + 'IJ');
                                            listaVrednostiIdevaFakture.push(tableData[i][0]);
                                        }
                                    }
                                    row[$scope.insertingValue[j].columnName] = tableData[i][j];
                                } else {
                                    row[$scope.insertingValue[j].columnName] = tableData[i][j];
                                    row[$scope.insertingValue[j].lookupField.name] = tableData[i][j];
                                }
                            }
                            rows.push(row);
                        }

                        $scope.listaVrednostiIdevaFakture = listaVrednostiIdevaFakture;

                        $scope.tableColumns = tableColumns;

                        console.log($scope.tableColumns);
                        for (var d = 0; d < tableColumns.length; d++) {
                            var capitalized = '';
                            if (tableColumns[d].name.includes('_')) {
                                var tableName = tableColumns[d].name.replace('_', ' ').toLowerCase();

                                var capital = tableName.substring(0, 1).toUpperCase();
                                capitalized = capital + tableName.substring(1);
                            } else {
                                var tableName = tableColumns[d].name.toLowerCase();
                                var capital = tableName.substring(0, 1).toUpperCase();
                                capitalized = capital + tableName.substring(1);
                            }

                            columnDefs.push({
                                headerName: capitalized,
                                field: tableColumns[d].name
                            });
                            /*
                            $scope.insertingValue.push({
                                columnName: tableColumns[d].name,
                                columnType: tableColumns[d].type,
                                columnValue: ''
                            });*/
                        }

                        gridOptions.api.setRowData(rows);
                        sizeToFit();

                        //selekcija prvog reda odmah na pocetku
                        /*
                        gridOptions.api.forEachNode(function(node) {
                            if (node.id == 0) {
                                node.setSelected(true, true);
                            }
                        });
                        */
                        TableOperations.setRefreshMethod(refreshTable);
                        TableOperations.setGridApi(gridOptions.api);
                    });
                });
            });

        };


        loadTable();
        //   $scope.goFirst();


        function onRefreshAll() {
            angular.element(document).ready(function() {
                gridOptions.api.refreshView();
            });
        }



        $scope.commit = function() {
            $scope.state = StateService.getState();
            var states = StateService.getStates();
            if ($scope.state === states.ADD) {
                console.log('sel row');
                console.log($scope.selectedRowData);
                for (var i = 0; i < $scope.insertingValue.length; i++) {
                    $scope.selectedRowData[i]['columnName'] = $scope.insertingValue[i]['columnName'];
                    $scope.selectedRowData[i]['columnType'] = $scope.insertingValue[i]['columnType'];
                }

                TableService.insertIntoTable($stateParams.tableName, $scope.selectedRowData, function() {
                    refreshTable();
                });
            } else if ($scope.state === states.EDIT) {
                var sendObj = {
                    tableRow: $scope.selectedRow,
                    changedRow: $scope.selectedRowData
                };
                var tID = $scope.insertingValue[0].tableId;
                tID = tID.toUpperCase().replace(' ', '_');
                var elementIDvalue = '';
                for (var i = 0; i < $scope.selectedRowData.length; i++) {
                    var elementId = $scope.selectedRowData[0]['columnName'].toUpperCase().replace(' ', '_');
                    if (elementId === tID) {
                        elementIDvalue = $scope.selectedRowData[0]['columnValue'];
                        break;
                    }
                }
                TableService.updateElementInTable($stateParams.tableName, tID, elementIDvalue, sendObj, function(resultMsg) {
                    if (resultMsg === 'Slog je obrisan od strane drugog korisnika') {
                        openErrorDialog('Slog je obrisan od strane drugog korisnika', function() {
                            refreshTable();
                        });
                    } else if (resultMsg === 'Slog je promenjen od strane drugog korisnika. Molim vas, pogledajte njegovu trenutnu vrednost') {
                        openErrorDialog('Slog je promenjen od strane drugog korisnika. Molim vas, pogledajte njegovu trenutnu vrednost', function() {
                            refreshTable();
                        });
                    } else {
                        refreshTable();
                    }

                });
            } else if ($scope.state === states.SEARCH) {

                TableService.searchTable($stateParams.tableName, $scope.selectedRowData, function(tableData) {
                    var rows = [];
                    console.log('result');
                    console.log(tableData);
                    for (var i = 0; i < tableData.length; i++) {
                        var row = {};
                        for (var j = 0; j < $scope.insertingValue.length; j++) {
                            row[$scope.insertingValue[j].columnName] = tableData[i][j];
                        }
                        rows.push(row);
                    }

                    gridOptions.api.setRowData(rows);


                    // goFirst()
                    $scope.goFirst();

                    sizeToFit();
                    onRefreshAll();

                });

            }

        };

        $scope.goFirst = function() {
            var lastRow = gridOptions.api.getModel().rowsToDisplay.length;
            gridOptions.api.forEachNode(function(node) {

                if (node.id == 0) {

                    node.setSelected(true, true);
                }
            });

        }

        $scope.goLast = function() {
            var lastRow = gridOptions.api.getModel().rowsToDisplay.length;
            gridOptions.api.forEachNode(function(node) {

                if (node.id == lastRow - 1) {

                    node.setSelected(true, true);
                }
            });
        };

        $scope.goNext = function() {

            var sledeci = 0;

            gridOptions.api.forEachNode(function(node) {
                if (node.isSelected()) {
                    sledeci = node.id + 1;
                }
            });

            gridOptions.api.forEachNode(function(node) {
                if (node.id == sledeci) {
                    node.setSelected(true, true);
                }
            });
        };

        $scope.goPrevious = function() {

            var prethodni = 0;

            gridOptions.api.forEachNode(function(node) {
                if (node.isSelected()) {
                    sledeci = node.id - 1;
                }
            });
            gridOptions.api.forEachNode(function(node) {
                if (node.id == sledeci) {
                    node.setSelected(true, true);
                }
            });
        };


        $scope.nextForm = function(column) {
            console.log('Ispis');
            console.log(column);

        };

        $scope.capitalize = function(str) {
            return UtilsService.capitalize(str);
        };

        $scope.reports = function(value) {
            TableService.callIReports(value);
        };

        $scope.reportDate = function(datumFrom, datumUntil) {
            TableService.callIReportsDate(datumFrom, datumUntil);
        };

        $scope.izvezi = function(value) {
            TableService.izveziXml(value);
        };

        $scope.iznosPDVa = function() {
        	//13 iznos pdva
        	//8 kolicina
        	//5 cena bez pdva
        	//10 rabat
        	//11 stopa pdv-a
        	//7 ukupan iznos sa pdvo-om
        	//12 osnovica pdv-a
        	
        	
          $scope.selectedRowData[13].columnValue = (($scope.selectedRowData[8].columnValue * $scope.selectedRowData[5].columnValue) -  $scope.selectedRowData[10].columnValue ) * $scope.selectedRowData[11].columnValue;
          console.log('Rez ' + $scope.selectedRowData[13].columnValue);
          $scope.selectedRowData[7].columnValue = $scope.selectedRowData[8].columnValue * $scope.selectedRowData[5].columnValue - $scope.selectedRowData[10].columnValue + $scope.selectedRowData[13].columnValue;
        };
        $scope.rabat = function() {
          $scope.selectedRowData[12].columnValue = $scope.selectedRowData[8].columnValue * $scope.selectedRowData[5].columnValue - $scope.selectedRowData[10].columnValue;
          //da bi se i ostala polja refreshovala
          $scope.selectedRowData[13].columnValue = $scope.selectedRowData[12].columnValue * $scope.selectedRowData[11].columnValue;
          $scope.selectedRowData[7].columnValue = $scope.selectedRowData[8].columnValue * $scope.selectedRowData[5].columnValue - $scope.selectedRowData[10].columnValue + $scope.selectedRowData[13].columnValue;

        };



    };




    app.controller('tableController', tableController);
    app.config(function($stateProvider) {
        $stateProvider
            .state('dashboard.table', {
                url: '/table/:tableName',
                templateUrl: '/Templates/table.html',
                controller: 'tableController'
            });
    });


})(angular);
