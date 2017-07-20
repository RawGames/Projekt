//
//  GSMobileBannerAdView.h
//  ConversantSDK
//
//  Created by Justine DiPrete on 11/11/11.
//  Copyright 2014 Conversant
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//

#import "GSBannerAdView.h"

extern const CGFloat kGSMobileBannerWidth;
extern const CGFloat kGSMobileBannerHeight;
extern NSString * const kGSMobileBannerParameter;

/**
 * A subclass of the GSBannerAdView that can be used to fetch and display 
 * Mobile banner ads. 
 *
 * This conforms to the GSAd protocol.
 */
@interface GSMobileBannerAdView : GSBannerAdView

@end
