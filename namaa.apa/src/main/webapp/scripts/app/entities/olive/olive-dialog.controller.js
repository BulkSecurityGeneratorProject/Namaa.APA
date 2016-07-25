'use strict';

angular.module('namaaApaApp').controller('OliveDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Olive', 'User',
        function($scope, $stateParams, $modalInstance, entity, Olive, User) {

        $scope.olive = entity;
            $scope.olive.watered=0.0;
            $scope.olive.nonWatered=0.0;
            $scope.olive.oliveOil=0.0;
        $scope.users = User.query();
        $scope.load = function(id) {
            Olive.get({id : id}, function(result) {
                $scope.olive = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('namaaApaApp:oliveUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if($scope.olive.oliveOil == $scope.olive.watered+$scope.olive.nonWatered) {
                if ($scope.olive.id != null) {
                    Olive.update($scope.olive, onSaveFinished);
                } else {
                    Olive.save($scope.olive, onSaveFinished);
                }
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
