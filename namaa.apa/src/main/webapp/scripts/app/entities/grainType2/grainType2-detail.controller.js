'use strict';

angular.module('namaaApaApp')
    .controller('GrainType2DetailController', function ($scope, $rootScope, $stateParams, entity, GrainType2, User) {
        $scope.grainType2 = entity;
        $scope.load = function (id) {
            GrainType2.get({id: id}, function(result) {
                $scope.grainType2 = result;
            });
        };
        $rootScope.$on('namaaApaApp:grainType2Update', function(event, result) {
            $scope.grainType2 = result;
        });
    });
