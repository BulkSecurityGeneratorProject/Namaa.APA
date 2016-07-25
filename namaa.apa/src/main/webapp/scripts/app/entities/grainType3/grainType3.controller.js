'use strict';

angular.module('namaaApaApp')
    .controller('GrainType3Controller', function ($scope, GrainType3) {
        $scope.grainType3s = [];
        $scope.loadAll = function() {
            GrainType3.query(function(result) {
               $scope.grainType3s = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            GrainType3.get({id: id}, function(result) {
                $scope.grainType3 = result;
                $('#deleteGrainType3Confirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            GrainType3.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteGrainType3Confirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.grainType3 = {chickpeas: null, beans: null, cowpea: null, lathyrus: null, id: null};
        };
    });
