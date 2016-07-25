'use strict';

angular.module('namaaApaApp')
    .controller('GrainType1Controller', function ($scope, GrainType1) {
        $scope.grainType1s = [];
        $scope.loadAll = function() {
            GrainType1.query(function(result) {
               $scope.grainType1s = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            GrainType1.get({id: id}, function(result) {
                $scope.grainType1 = result;
                $('#deleteGrainType1Confirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            GrainType1.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteGrainType1Confirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.grainType1 = {spelt: null, corn: null, millet: null, id: null};
        };
    });
