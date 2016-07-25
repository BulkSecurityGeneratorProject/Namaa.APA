'use strict';

angular.module('namaaApaApp')
    .controller('GrainType2Controller', function ($scope, GrainType2) {
        $scope.grainType2s = [];
        $scope.loadAll = function() {
            GrainType2.query(function(result) {
               $scope.grainType2s = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            GrainType2.get({id: id}, function(result) {
                $scope.grainType2 = result;
                $('#deleteGrainType2Confirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            GrainType2.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteGrainType2Confirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.grainType2 = {wheat: null, barley: null, id: null};
        };
    });
