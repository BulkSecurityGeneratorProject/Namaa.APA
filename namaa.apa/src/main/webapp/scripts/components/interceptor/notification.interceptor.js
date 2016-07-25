 'use strict';

angular.module('namaaApaApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-namaaApaApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-namaaApaApp-params')});
                }
                return response;
            },
        };
    });