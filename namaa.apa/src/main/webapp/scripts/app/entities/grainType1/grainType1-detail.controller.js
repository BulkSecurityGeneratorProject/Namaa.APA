'use strict';

angular.module('namaaApaApp')
    .controller('GrainType1DetailController', function ($scope, $rootScope, $stateParams, entity, GrainType1, User) {
        $scope.grainType1 = entity;
        $scope.load = function (id) {
            GrainType1.get({id: id}, function(result) {
                $scope.grainType1 = result;
            });
        };
        $rootScope.$on('namaaApaApp:grainType1Update', function(event, result) {
            $scope.grainType1 = result;
        });
    });
