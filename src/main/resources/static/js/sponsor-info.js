paypal.Buttons({
  createSubscription: function(data, actions) {
    return actions.subscription.create({
      'plan_id': 'P-97R06541AL338905HL2DMHRQ',
      'quantity': $('#num-selected-children').val()
    });
  },

  onApprove: function(data, actions) {
    alert('You have successfully created subscription ' + data.subscriptionID);
    $('#sponsor-info-form').submit();
  }


}).render('#paypal-button-container');

