'use strict';

angular.module('namaaApaApp').controller('GrainType2DialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'GrainType2', 'User',
        function($scope, $stateParams, $modalInstance, entity, GrainType2, User) {

        $scope.grainType2 = entity;
            $scope.grainType2.watered=0.0;
            $scope.grainType2.nonWatered=0.0;
            $scope.grainType2.wheat=0.0;
            $scope.grainType2.barley=0.0;

            $scope.users = User.query();
        $scope.load = function(id) {
            GrainType2.get({id : id}, function(result) {
                $scope.grainType2 = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('namaaApaApp:grainType2Update', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if($scope.grainType2.wheat+$scope.grainType2.barley == $scope.grainType2.watered+$scope.grainType2.nonWatered) {
                if ($scope.grainType2.id != null) {
                    GrainType2.update($scope.grainType2, onSaveFinished);
                } else {
                    GrainType2.save($scope.grainType2, onSaveFinished);
                }
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
