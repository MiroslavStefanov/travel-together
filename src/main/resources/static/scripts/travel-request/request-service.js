function createRequest(travelId, userId, successCallback) {
    let request = {
        travel: '/travel_api/' + travelId,
        user: '/users/' + userId,
    };
    $.ajax({
        type: 'POST',
        url: '/travelRequests',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(request),
        beforeSend: (request) => {
            let _tc = $("meta[name='_csrf']").attr("content");
            let _hc = $("meta[name='_csrf_header']").attr("content");
            request.setRequestHeader(_hc, _tc);
        },
        success: successCallback
    })
}

function deleteRequest(requestId, successCallback) {
    if(requestId !== null) {
        $.ajax({
            type: 'DELETE',
            url: '/travelRequests/' + requestId,
            dataType: 'json',
            contentType: 'application/json',
            beforeSend: (request) => {
                let _tc = $("meta[name='_csrf']").attr("content");
                let _hc = $("meta[name='_csrf_header']").attr("content");
                request.setRequestHeader(_hc, _tc);
            },
            success: successCallback
        })
    }
}

function acceptRequest(requestId, travelId, userId, successCallBack) {
    deleteRequest(requestId, () => {
        $.ajax({
            type: 'PATCH',
            url: '/travels/' + travelId + '/add',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify({
                attendantId: userId
            }),
            beforeSend: (request) => {
                let _tc = $("meta[name='_csrf']").attr("content");
                let _hc = $("meta[name='_csrf_header']").attr("content");
                request.setRequestHeader(_hc, _tc);
            },
            success: successCallBack
        });
    });
}

function removeAttendant(travelId, userId, successCallback) {
    $.ajax({
        type: 'PATCH',
        url: '/travels/' + travelId + '/remove',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            attendantId: userId
        }),
        beforeSend: request => {
            let _tc = $("meta[name='_csrf']").attr("content");
            let _hc = $("meta[name='_csrf_header']").attr("content");
            request.setRequestHeader(_hc, _tc);
        },
        success: successCallback
    });
}